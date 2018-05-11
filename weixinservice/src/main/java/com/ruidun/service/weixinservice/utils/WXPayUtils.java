package com.ruidun.service.weixinservice.utils;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.net.ssl.HttpsURLConnection;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import java.io.*;

import java.net.URL;
import java.security.KeyStore;
import java.util.*;

/**
 * Created by chunxu_wu on 2015/9/4.
 */
public class WXPayUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(WXPayUtils.class);

    public static String createNoncestr(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static String createSign(String characterEncoding, SortedMap<Object, Object> parameters, String weixinKey) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + weixinKey);//ThirdPartyConfig.WX_KEY
        LOGGER.info("finalUrl Report status.============== {}",sb);
        String sign = MD5Util.MD5Encode(sb.toString()).toUpperCase();
        return sign;
    }

    public static String createSign(Map<String,Object> map, String weixinKey) {
        ArrayList<String> list = new ArrayList<String>();
        for(Map.Entry<String,Object> entry:map.entrySet()){
            if(entry.getValue()!=""){
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + weixinKey;
        //Util.log("Sign Before MD5:" + result);
        result = MD5Util.MD5Encode(result).toUpperCase();
        //Util.log("Sign Result:" + result);
        return result;
    }

    public static String getRequestXml(SortedMap<Object,Object> parameters){
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            String v = (String)entry.getValue();
            if ("attach".equalsIgnoreCase(k)) {
                sb.append("<"+k+">"+"<![CDATA["+v+"]]></"+k+">");
            }else if (!"sign".equalsIgnoreCase(k)){
                sb.append("<"+k+">"+v+"</"+k+">");
            }
        }
        sb.append("<sign>"+parameters.get("sign")+"</sign>");
        sb.append("</xml>");
        LOGGER.info("sbsbsbsbsbsbs========{}",sb);
        return sb.toString();
    }

    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            return buffer.toString();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static  boolean checkIsSignValidFromResponse(Map<String,Object> map, String appKey) {
        String signFromAPIResponse = map.get("sign").toString();
        if(signFromAPIResponse=="" || signFromAPIResponse == null){
            return false;
        }
        //清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
        map.put("sign","");
        //将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
        String signForAPIResponse = createSign(map, appKey);

        if(!signForAPIResponse.equals(signFromAPIResponse)){
            //签名验不过，表示这个API返回的数据有可能已经被篡改了
            return false;
        }
        return true;
    }

    /**
     * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
     */
    public static String createSign1(SortedMap<String, String> packageParams, String AppKey) {
        StringBuffer sb = new StringBuffer();
        Set es = packageParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + AppKey);
        String sign = MD5Util.MD5Encode2(sb.toString(), "UTF-8").toUpperCase();
        return sign;
    }

    public static String wxPayRefund(String out_trade_no,String total_fee) {
        StringBuffer xml = new StringBuffer();
        String data = null;
        try {
            String nonceStr = genNonceStr();
            xml.append("</xml>");
            SortedMap<String,String> parameters = new TreeMap<String,String>();
            parameters.put("appid","wx01af434429e29725");
            parameters.put("mch_id", "1503101741");
            parameters.put("nonce_str", nonceStr);
            parameters.put("out_trade_no", out_trade_no);
            parameters.put("out_refund_no", nonceStr);
            parameters.put("fee_type", "CNY");
            parameters.put("total_fee", total_fee);
            parameters.put("refund_fee", total_fee);
            parameters.put("op_user_id", "1503101741");
            parameters.put("sign", createSign1(parameters, "518eb9368cc423fce6160463ed157a0e"));
            data =SortedMaptoXml(parameters);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
        return data;
    }
    /**
     * @Author: HONGLINCHEN
     * @Description:请求值转换为xml格式 SortedMap转xml
     * @param params
     * @Date: 2017-9-7 17:18
     */
    private static String SortedMaptoXml(SortedMap<String,String> params) {
        StringBuilder sb = new StringBuilder();
        Set es = params.entrySet();
        Iterator it = es.iterator();
        sb.append("<xml>\n");
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            sb.append("<"+k+">");
            sb.append(v);
            sb.append("</"+k+">\n");
        }
        sb.append("</xml>");
        return sb.toString();
    }
    /**
     * 生成32位随机数字
     */
    public static String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    /**
     * 证书使用
     * 微信退款
     */
    public static String wxPayBack(String url, String data) throws Exception {
        KeyStore keyStore  = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File("D:\\微信商户平台支付证书\\apiclient_cert.p12"));
        String result="";
        try {
            keyStore.load(instream, "1503101741".toCharArray());
        } finally {
            instream.close();
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, "1503101741".toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        try {
            HttpPost httppost = new HttpPost("https://api.mch.weixin.qq.com/secapi/pay/refund");
            StringEntity entitys = new StringEntity(data);
            httppost.setEntity(entitys);
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
                    String text="";
                    String t="";
                    while ((text=bufferedReader.readLine()) != null) {
                        t+=text;
                    }
                    byte[] temp=t.getBytes("gbk");//这里写原编码方式
                    String newStr=new String(temp,"utf-8");//这里写转换后的编码方式
                    result=newStr;
                }
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        return result;
    }

    /**
     * 获取随机字符串 Nonce Str
     *
     * @return String 随机字符串
     */
    public static String generateNonceStr() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
    }

}
