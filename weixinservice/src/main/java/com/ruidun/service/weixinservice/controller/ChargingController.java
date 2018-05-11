package com.ruidun.service.weixinservice.controller;

import com.ruidun.service.weixinservice.Bean.*;
import com.ruidun.service.weixinservice.model.*;
import com.ruidun.service.weixinservice.service.*;
import com.ruidun.service.weixinservice.utils.*;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.UUID;

@RestController
@RequestMapping(value = "/charger")
public class ChargingController {
    private static final Logger logger = LoggerFactory.getLogger(ChargingController.class);

    @Autowired
    private ChargingService chargingService;


    @RequestMapping(value ="/getnearcharging",method = { RequestMethod.POST})
    public ResponseEntity<JsonResult> getNearCharging (@RequestBody NearChargingBodyModel nearChargingBodyModel){
        JsonResult jsonResult = new JsonResult();
        NearChargingJsonResult nearChargingJsonResult = new NearChargingJsonResult();
        final int n;
        try {
            List<NearChargingModel> nearChargingModelList = chargingService.selectNearChargingInfo();
            List<MarkModel> markModelList=chargingService.selectMark();
            List<NearChargingModel> nearChargingModes = new ArrayList<>();
                for (int j = 0;j< markModelList.size();j++){
                    MarkModel markModel = markModelList.get(j);
                    int usedCount = 0;
                    int availableCount = 0;
                    boolean falg = true;
                    for (int i = 0;i< nearChargingModelList.size();i++){
                        NearChargingModel nearChargingModel = nearChargingModelList.get(i);
                    if (markModel.getLocationId()==(nearChargingModel.getLocationId())){
                        usedCount = usedCount+nearChargingModel.getUsedCount();
                        availableCount=availableCount+nearChargingModel.getAvailableCount();
                        if (falg==false){
                            nearChargingModelList.remove(i);
                            i--;
                        }
                        falg=false;
                    }
                }
                NearChargingModel nearChargingModel = nearChargingModelList.get(j);
                if (nearChargingBodyModel.getLat()==0||nearChargingBodyModel.getLng()==0){
                    nearChargingModel.setDistance(-1);
                }else {
                    double distance = getDistance(nearChargingBodyModel.getLat(),nearChargingBodyModel.getLng(),nearChargingModel.getLat(),nearChargingModel.getLng());
                    nearChargingModel.setDistance(distance);
                    nearChargingModel.setUsedCount(usedCount);
                    nearChargingModel.setAvailableCount(availableCount);
                }
            }
            for (int i=(nearChargingBodyModel.getPageindex()-1)*nearChargingBodyModel.getPagesize();i<(nearChargingBodyModel.getPageindex()-1)*nearChargingBodyModel.getPagesize()+nearChargingBodyModel.getPagesize();i++){
                if (i<nearChargingModelList.size()){
                    nearChargingModes.add(nearChargingModelList.get(i));
                }
            }
            Collections.sort(nearChargingModes, new Comparator<NearChargingModel>() {
                @Override
                public int compare(NearChargingModel o1, NearChargingModel o2) {
                    double temp = o1.getDistance()-o2.getDistance();
                    int a = 0;
                    if (temp>0) {
                        a = 1;
                    } else {
                        a = -1;
                    }
                    return a;
                }
            });
            jsonResult.setStatus(0);
            jsonResult.setMsg("请求成功");
            nearChargingJsonResult.setPageIndex(nearChargingBodyModel.getPageindex());
            nearChargingJsonResult.setPageSize(nearChargingBodyModel.getPagesize());
            nearChargingJsonResult.setContent(nearChargingModes);
            jsonResult.setData(nearChargingJsonResult);
        } catch (Exception e) {
            logger.error("Failed to get nearchargers. Error = {}", e.getMessage());
            jsonResult.setMsg("Failed to get nearchargers");
            jsonResult.setStatus(1);
            e.printStackTrace();
        }
        return ResponseEntity.ok(jsonResult);
    }


    @RequestMapping(value ="/getcharging",method = { RequestMethod.POST})
    public ResponseEntity<ChargingJsonResult> getCharging (@RequestBody ChargingBodyModel chargingBodyModel){
        ChargingJsonResult chargingJsonResult = new ChargingJsonResult();
        ChargingContentJsonResult chargingContentJsonResult = new ChargingContentJsonResult();
        try {
            int usedCount = 0;
            int availableCount = 0;
            String location = null;
            String locationDetail = null;
            double lat = 0;
            double lng = 0;
            double distance = 0;
            List<ChargingModel> chargingModelList = chargingService.selectCharging(chargingBodyModel.getLocationId());
            List<ChargingContentModel> chargingContentModels = chargingService.selectContenCharging(chargingBodyModel.getLocationId());
            for (int i = 0;i< chargingModelList.size();i++){
                ChargingModel chargingModel = chargingModelList.get(i);
                usedCount = usedCount+chargingModel.getUsedCount();
                availableCount = availableCount+chargingModel.getAvailableCount();
                if (i==0){
                    if (chargingBodyModel.getLat()==0||chargingBodyModel.getLng()==0){
                        distance = -1;
                    }else {
                        location = chargingModel.getLocation();
                        locationDetail = chargingModel.getLocationDetail();
                        lat = chargingModel.getLat();
                        lng = chargingModel.getLng();
                        distance = getDistance(chargingBodyModel.getLat(),chargingBodyModel.getLng(),lat,lng);
                    }

                }
            }
            chargingJsonResult.setStatus(0);
            chargingJsonResult.setMsg("请求成功");
            chargingContentJsonResult.setAvailableCountTotal(availableCount);
            chargingContentJsonResult.setUsedCountTotal(usedCount);
            chargingContentJsonResult.setLocation(location);
            chargingContentJsonResult.setLocationDetail(locationDetail);
            chargingContentJsonResult.setLat(lat);
            chargingContentJsonResult.setLng(lng);
            chargingContentJsonResult.setDistance(distance);
            chargingContentJsonResult.setContent(chargingContentModels);
            chargingJsonResult.setData(chargingContentJsonResult);
        } catch (Exception e) {
            logger.error("Failed to get charging. Error = {}", e.getMessage());
            chargingJsonResult.setMsg("Failed to get charging");
            chargingJsonResult.setStatus(1);
            e.printStackTrace();
        }
        return ResponseEntity.ok(chargingJsonResult);
    }

    @RequestMapping(value = "/getcollectioncharging",method = { RequestMethod.POST})
    public ResponseEntity<CollectionChargingJsonResult> getCollectionCharging (@RequestBody ChargingCollectionBodyModel chargingCollectionBodyModel){
        CollectionChargingJsonResult collectionChargingJsonResult = new CollectionChargingJsonResult();
        CollectionChargingContentJsonResult collectionChargingContentJsonResult = new CollectionChargingContentJsonResult();
        try {
            List<CollectionChargingModel> collectionChargingModelList = chargingService.selectcollectionCharging(chargingCollectionBodyModel.getUserId());
            for (CollectionChargingModel collectionChargingModel:collectionChargingModelList){
                if (chargingCollectionBodyModel.getLat() == 0||chargingCollectionBodyModel.getLng() == 0){
                    double distance = -1;
                    collectionChargingModel.setDistance(distance);
                }else {
                    double distance = getDistance(chargingCollectionBodyModel.getLat(),chargingCollectionBodyModel.getLng(),collectionChargingModel.getLat(),collectionChargingModel.getLng());
                    collectionChargingModel.setDistance(distance);
                }
            }
            Collections.sort(collectionChargingModelList, new Comparator<CollectionChargingModel>() {
                @Override
                public int compare(CollectionChargingModel o1, CollectionChargingModel o2) {
                    double temp = o1.getDistance()-o2.getDistance();
                    int a = 0;
                    if (temp>0) {
                        a = 1;
                    } else {
                        a = -1;
                    }
                    return a;
                }
            });
            collectionChargingJsonResult.setStatus(0);
            collectionChargingJsonResult.setMsg("请求成功");
            collectionChargingContentJsonResult.setContent(collectionChargingModelList);
            collectionChargingJsonResult.setData(collectionChargingContentJsonResult);

        } catch (Exception e) {
            logger.error("Failed to get collectioncharging. Error = {}", e.getMessage());
            collectionChargingJsonResult.setMsg("Failed to get collectioncharging");
            collectionChargingJsonResult.setStatus(1);
            e.printStackTrace();
        }
        return ResponseEntity.ok(collectionChargingJsonResult);
    }

    @RequestMapping(value = "/getslotcharging",method = { RequestMethod.POST})
    public ResponseEntity<SlotChargingJsonResult> getSlotCharging (@RequestBody SlotChargingBodyModel slotChargingBodyModel){
        SlotChargingJsonResult slotChargingJsonResult = new SlotChargingJsonResult();
        SlotChargingContentJsonResult slotChargingContentJsonResult = new SlotChargingContentJsonResult();
        try {
            List<SlotChargingModel> slotChargingModelList = chargingService.selectslotCharging(slotChargingBodyModel.getDeviceId());
            List<LocationModel> locationModelList = chargingService.selectlocation(slotChargingBodyModel.getDeviceId());
            LocationModel locationModel=locationModelList.get(0);
            double distance = 0;
            if (slotChargingBodyModel.getLat()==0||slotChargingBodyModel.getLng()==0) {
                 distance = -1;
            }else {
                 distance = getDistance(slotChargingBodyModel.getLat(),slotChargingBodyModel.getLng(),locationModel.getLat(),locationModel.getLng());
            }

            slotChargingJsonResult.setStatus(0);
            slotChargingJsonResult.setMsg("请求成功");
            slotChargingContentJsonResult.setDeviceId(slotChargingBodyModel.getDeviceId());
            slotChargingContentJsonResult.setLocation(locationModel.getLocation());
            slotChargingContentJsonResult.setLocationDetail(locationModel.getLocationDetail());
            slotChargingContentJsonResult.setLat(locationModel.getLat());
            slotChargingContentJsonResult.setLng(locationModel.getLng());
            slotChargingContentJsonResult.setDistance(distance);
            slotChargingContentJsonResult.setContent(slotChargingModelList);
            slotChargingJsonResult.setData(slotChargingContentJsonResult);
        } catch (Exception e) {
            logger.error("Failed to get slotcharging. Error = {}", e.getMessage());
            slotChargingJsonResult.setMsg("Failed to get slotcharging");
            slotChargingJsonResult.setStatus(1);
            e.printStackTrace();
        }
        return ResponseEntity.ok(slotChargingJsonResult);
    }
    @RequestMapping(value = "/getChargingProgress",method = { RequestMethod.POST})
    public ResponseEntity<NowChargingJsonResult> getChargingProgress (@RequestBody NowChargingBodyModel nowChargingBodyModel){
        NowChargingJsonResult nowChargingJsonResult = new NowChargingJsonResult();
        try {
            NowChargingModel nowChargingModel = chargingService.selectNowCharging(nowChargingBodyModel.getDeviceId(),nowChargingBodyModel.getSlotIndex());
            long l = getSeconds(nowChargingModel.getTime());
            long l2 = getSeconds(nowChargingModel.getChargingTime());
            double progress = (double) l2/(l+l2)*100;
            int p = (int)progress;

            nowChargingModel.setProgress(p);
            nowChargingJsonResult.setStatus(0);
            nowChargingJsonResult.setData(nowChargingModel);
            nowChargingJsonResult.setMsg("请求成功");
        } catch (Exception e) {
            logger.error("Failed to get ChargingProgress. Error = {}", e.getMessage());
            nowChargingJsonResult.setMsg("Failed to get ChargingProgress");
            nowChargingJsonResult.setStatus(1);
            e.printStackTrace();
        }
        return ResponseEntity.ok(nowChargingJsonResult);
    }

    @RequestMapping(value = "/getsearchWordcharging",method = { RequestMethod.POST})
    public ResponseEntity<SearchWordChargingJsonResult> getSearchCharging (@RequestBody SearchWordChargingBodyModel searchWordChargingBodyModel){
        SearchWordChargingJsonResult searchWordChargingJsonResult = new SearchWordChargingJsonResult();
        SearchWordChargingContentJsonResult searchWordChargingContentJsonResult = new SearchWordChargingContentJsonResult();
        try {
            List<SearchWordChargingModel> searchWordChargingModelList = chargingService.selectSearchWordCharging(searchWordChargingBodyModel.getSearchWord());
            List<MarkModel> markModelList=chargingService.selectMark();
            for (int j = 0;j<markModelList.size();j++){
                MarkModel markModel=markModelList.get(j);
                int usedCount = 0;
                int availableCount = 0;
                boolean falg = true;
                for (int i=0;i<searchWordChargingModelList.size();i++){
                    SearchWordChargingModel searchWordChargingModel=searchWordChargingModelList.get(i);
                    if (markModel.getLocationId()==(searchWordChargingModel.getLocationId())){
                        usedCount=usedCount+searchWordChargingModel.getUsedCount();
                        availableCount=availableCount+searchWordChargingModel.getAvailableCount();
                        if (falg == false){
                            searchWordChargingModelList.remove(i);
                            i--;
                        }
                        falg = false;
                    }
                }
                if (falg == true){
                    markModelList.remove(j);
                    j--;
                }else {
                    SearchWordChargingModel searchWordChargingModel=searchWordChargingModelList.get(j);
                    double distance = getDistance(searchWordChargingBodyModel.getLat(),searchWordChargingBodyModel.getLng(),searchWordChargingModel.getLat(),searchWordChargingModel.getLng());
                    searchWordChargingModel.setDistance(distance);
                    searchWordChargingModel.setUsedCount(usedCount);
                    searchWordChargingModel.setAvailableCount(availableCount);
                }
            }
            for (SearchWordChargingModel searchWordChargingModel : searchWordChargingModelList){
                if (!searchWordChargingModel.getCity().equals(searchWordChargingBodyModel.getCity())){
                    searchWordChargingModelList.remove(searchWordChargingModel);
                }
            }
            Collections.sort(searchWordChargingModelList, new Comparator<SearchWordChargingModel>() {
                @Override
                public int compare(SearchWordChargingModel o1, SearchWordChargingModel o2) {
                    double temp = o1.getDistance()-o2.getDistance();
                    int a = 0;
                    if (temp>0) {
                        a = 1;
                    } else {
                        a = -1;
                    }
                    return a;
                }
            });
            searchWordChargingJsonResult.setStatus(0);
            searchWordChargingJsonResult.setMsg("请求成功");
            searchWordChargingContentJsonResult.setContent(searchWordChargingModelList);
            searchWordChargingJsonResult.setData(searchWordChargingContentJsonResult);
        } catch (Exception e) {
            logger.error("Failed to get searchWordcharging. Error = {}", e.getMessage());
            searchWordChargingJsonResult.setMsg("Failed to get searchWordcharging");
            searchWordChargingJsonResult.setStatus(1);
            e.printStackTrace();
        }
        return ResponseEntity.ok(searchWordChargingJsonResult);
    }

    @RequestMapping(value = "/getmycharging",method = { RequestMethod.POST})
    public ResponseEntity<MyChargingJsonResult> getMyCharging (@RequestBody MyChargingBodyModel myChargingBodyModel){
        MyChargingJsonResult myChargingJsonResult = new MyChargingJsonResult();
        try {
            List<MyChargingModel> myChargingModelList = chargingService.selectmycharging(myChargingBodyModel.getUserId());
            for (MyChargingModel myChargingModel : myChargingModelList){
                long l = getSeconds(myChargingModel.getTime());
                long l2 = getSeconds(myChargingModel.getChargingTime());
                double progress = (double) l2 / (l + l2) * 100;
                int p = (int) progress;
                myChargingModel.setProgress(p);
            }
            myChargingJsonResult.setStatus(0);
            myChargingJsonResult.setData(myChargingModelList);
            myChargingJsonResult.setMsg("请求成功");
        } catch (Exception e) {
            logger.error("Failed to get mycharging. Error = {}", e.getMessage());
            myChargingJsonResult.setMsg("Failed to get mycharging");
            myChargingJsonResult.setStatus(1);
            e.printStackTrace();
        }
        return ResponseEntity.ok(myChargingJsonResult);
    }

    @RequestMapping(value ="/getrecordcharging",method = { RequestMethod.POST})
    public ResponseEntity<RecordChargingJsonResult> getRecordCharging (@RequestBody RecordChargingBodyModel recordChargingBodyModel){
        RecordChargingJsonResult recordChargingJsonResult = new RecordChargingJsonResult();
        RecordChargingContentJsonResult recordChargingContentJsonResult = new RecordChargingContentJsonResult();
        int completeOrder = 0;
        int availableOrder = 0;
        try {
            List<RecordChargingModel> recordChargingModelList = chargingService.selectrecordcharging(recordChargingBodyModel.getUserId());
            for (RecordChargingModel recordChargingModel:recordChargingModelList){
                    recordChargingModel.setPaytime(timeStamp2Date(recordChargingModel.getPaytime(),null));
                if (recordChargingModel.getStatus()==0){
                    completeOrder = completeOrder + 1;
                    recordChargingModel.setState("付款完成");
                }else {
                    availableOrder = availableOrder + 1;
                    recordChargingModel.setState("付款失败");
                }

            }
            recordChargingJsonResult.setStatus(0);
            recordChargingJsonResult.setMsg("请求成功");
            recordChargingContentJsonResult.setFinishedOrder(completeOrder);
            recordChargingContentJsonResult.setCancalledOrder(availableOrder);
            recordChargingContentJsonResult.setContent(recordChargingModelList);
            recordChargingJsonResult.setData(recordChargingContentJsonResult);
        } catch (Exception e) {
            logger.error("Failed to get recordcharging. Error = {}", e.getMessage());
            recordChargingJsonResult.setMsg("Failed to get recordcharging");
            recordChargingJsonResult.setStatus(1);
            e.printStackTrace();
        }
        return ResponseEntity.ok(recordChargingJsonResult);
    }

    @RequestMapping(value = "/getpaycharging",method = { RequestMethod.POST})
    public ResponseEntity<PayChargingJsonResult> getPayCharging (@RequestBody PayChargingBodyModel payChargingBodyModel){
        PayChargingJsonResult payChargingJsonResult = new PayChargingJsonResult();
        try {
            String deviceId = payChargingBodyModel.getDeviceId();
            int slotIndex = payChargingBodyModel.getSlotIndex();
            PayChargingModel payChargingModel = chargingService.selectpayCharging(deviceId, slotIndex);
            payChargingJsonResult.setData(payChargingModel);
            payChargingJsonResult.setMsg("请求成功");
        } catch (Exception e) {
            logger.error("Failed to get paycharging. Error = {}", e.getMessage());
            payChargingJsonResult.setMsg("Failed to get paycharging");
            payChargingJsonResult.setStatus(1);
            e.printStackTrace();
        }
        return ResponseEntity.ok(payChargingJsonResult);
    }

    @RequestMapping(value = "/config", method = { RequestMethod.POST})
    public void getConfig(HttpServletRequest request, HttpServletResponse response, @RequestBody UrlModel userurl) {
        try {
            response.setCharacterEncoding("UTF-8");
            String appId = "wx01af434429e29725";
            // 获取页面路径(前端获取时采用location.href.split('#')[0]获取url)
            String url = userurl.getUrl();
            // 获取ticket
            Map map = AccessTokenUtil.getInstance().getAccessTokenAndJsapiTicket();
            // 获取Unix时间戳(java时间戳为13位,所以要截取最后3位,保留前10位)
            String timeStamp = Long.toString(System.currentTimeMillis() / 1000);
            String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
            // 注意这里参数名必须全部小写，且必须有序
            String sign = "jsapi_ticket=" + map.get("jsapiticket") + "&noncestr=" + noncestr + "&timestamp=" + timeStamp + "&url=" + url;
            logger.info("Report status. {}", url);
            SHA1 sha1 = new SHA1();
            String signature = sha1.getSHA1(sign);
            // 组装完毕，回传
            Map<String, Object> ret = new HashMap<String, Object>();
            ret.put("appId", appId);
            ret.put("timestamp", timeStamp);
            ret.put("nonceStr", noncestr);
            ret.put("signature", signature);
            JSONObject json = JSONObject.fromObject(ret);
            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
            out.close();
        } catch (Exception e) {
            logger.error("Failed to get config. Error = {}", e.getMessage());
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "/getOpenId", method = { RequestMethod.GET})
    public void getOpenIdForFe(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String appId = "wx01af434429e29725";
        String callBackUrl = "http://www.shouyifenxi.com/charger/getOpenIdJson?state=" + 1;
        String redirect_uri = URLEncoder.encode(callBackUrl, "UTF-8");
        String weixinUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?redirect_uri=";
        String finalUrl = weixinUrl + redirect_uri + "&appid=" + appId + "&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
        logger.info("finalUrl Report status.============== {}", finalUrl);
        response.setStatus(301);
        response.sendRedirect(finalUrl);
    }

    @RequestMapping(value = "/getOpenIdJson", method = { RequestMethod.GET})
    public void getOpenIdJson(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String codeValue = request.getParameter("code");
        logger.info("codeValue Report status.============== {}", codeValue);
        String result = HttpClient.get("https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx01af434429e29725&secret=5e517cc30f12e4ffe8b4a2a183fe88e7&code=" + codeValue + "&grant_type=authorization_code");
        logger.info("result Report status. {}", result);
        JSONObject jsonObject = JSONObject.fromObject(result);
        String openid = jsonObject.get("openid").toString();
        logger.info("openid Report status. {}", openid);
        Map<String, Object> ret = new HashMap<>();
        ret.put("status", 0);
        ret.put("msg","获取openid成功");
        ret.put("openId",openid);
        JSONObject json = JSONObject.fromObject(ret);
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
    }

    @RequestMapping(value = "/verify", method = { RequestMethod.GET})
    public void redirectToCodeUrl(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String chargerId = request.getParameter("deviceId");
        String slotId = request.getParameter("slotIndex");
        String appId = "wx01af434429e29725";
        String callBackUrl = "http://www.shouyifenxi.com/charger/getOpenIdAndRedirect?deviceId=" + chargerId
                + "&slotIndex=" + slotId+"&state="+1;
        String redirect_uri = URLEncoder.encode(callBackUrl, "UTF-8");
        String weixinUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?redirect_uri=";
        String finalUrl = weixinUrl + redirect_uri+ "&appid="+appId+"&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
        logger.info("finalUrl Report status.============== {}",finalUrl);
        response.setStatus(301);
        response.sendRedirect(finalUrl);
    }

    @RequestMapping(value = "/getOpenIdAndRedirect", method = { RequestMethod.GET})
    public void getOpenIdAndRedirect(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String chargerId = request.getParameter("deviceId");
        String slotId = request.getParameter("slotIndex");
        String codeValue = request.getParameter("code");
        logger.info("codeValue Report status.============== {}",codeValue);
        String result = HttpClient.get("https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx01af434429e29725&secret=5e517cc30f12e4ffe8b4a2a183fe88e7&code="+codeValue+"&grant_type=authorization_code");
        logger.info("result Report status. {}",result);
        JSONObject jsonObject = JSONObject.fromObject(result);
        String  openid= jsonObject.get("openid").toString();
        logger.info("openid Report status. {}",openid);


        Map map=  AccessTokenUtil.getInstance().getAccessTokenAndJsapiTicket();
        logger.info("accesstoken  Report status. {}",map.get("access_token"));
        String infoJson = HttpClient.get("https://api.weixin.qq.com/cgi-bin/user/info?access_token="+map.get("access_token")+"&openid="+openid+"&lang=zh_CN");
        logger.info("fanJson Report status. {}",infoJson);
        JSONObject jsonObject1 = JSONObject.fromObject(infoJson);
        int subscribe = jsonObject1.getInt("subscribe");
        response.setStatus(301);
        if (subscribe == 0) {
            String url = "http://www.shouyifenxi.com/dist/page/qrcode.html?openid="+openid;
            response.sendRedirect(url);
        } else {
            String url = "http://www.shouyifenxi.com/dist/page/charge.html?openid="+openid+"&deviceId="+chargerId+"&slotIndex="+slotId;
            response.sendRedirect(url);
        }

    }

    @RequestMapping(value = "/getUserProfile", method = { RequestMethod.POST})
    public ResponseEntity<MyCentreJsonResult> getMyCentreOpenId(HttpServletRequest request, HttpServletResponse response, @RequestBody GetMyModel getMyModel)
            throws ServletException, IOException {
        MyCentreJsonResult myCentreJsonResult = new MyCentreJsonResult();
        try {
            Map map=  AccessTokenUtil.getInstance().getAccessTokenAndJsapiTicket();
            logger.info("accesstoken  Report status. {}",map.get("access_token"));
            String infoJson = HttpClient.get("https://api.weixin.qq.com/cgi-bin/user/info?access_token="+map.get("access_token")+"&openid="+getMyModel.getOpenId()+"&lang=zh_CN");
            logger.info("fanJson Report status. {}",infoJson);
            JSONObject jsonObject1 = JSONObject.fromObject(infoJson);
            MyCentreModel myCentreModel=new MyCentreModel();
            myCentreModel.setAvatar(jsonObject1.getString("headimgurl"));
            myCentreModel.setNickname(jsonObject1.getString("nickname"));
            myCentreJsonResult.setStatus(0);
            myCentreJsonResult.setData(myCentreModel);
            myCentreJsonResult.setMsg("请求成功");
        } catch (Exception e) {
            logger.error("Failed to get UserProfile. Error = {}", e.getMessage());
            myCentreJsonResult.setMsg("Failed to get UserProfile");
            myCentreJsonResult.setStatus(1);
            e.printStackTrace();
        }
        return ResponseEntity.ok(myCentreJsonResult);

    }

    private long getSeconds(String time){
        long seconds=0;
        try {
            SimpleDateFormat sDateFormat = new SimpleDateFormat("HH:mm:ss");
            Date time1 = sDateFormat.parse(time);
            seconds = time1.getHours()*3600+time1.getMinutes()*60+time1.getSeconds();
        }catch (Exception e){
            e.printStackTrace();
        }
        return seconds;
    }

    private double getDistance(double bodylat,double bodylng,double lat,double lng){
        double[] gps = GPSUtil.gps84_To_Gcj02(bodylat,bodylng);
        Double distance =  DistanceUtil.getDistance(gps[0],gps[1],lat,lng);
        BigDecimal b = new BigDecimal(distance);
        distance = b.setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
        return distance;
    }

    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }

}
