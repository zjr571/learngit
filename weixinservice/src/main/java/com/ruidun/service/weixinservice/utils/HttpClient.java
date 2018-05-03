package com.ruidun.service.weixinservice.utils;




import com.ruidun.service.weixinservice.controller.ChargingController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.*;
import java.util.Map;

public class HttpClient {
    public static String get(String url) {
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);

            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
          e.printStackTrace();
        }

        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }
    public static String doPost(String httpUrl, String param) {
        HttpURLConnection connection = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedReader br = null;
        String result = null;
        try {
            URL url = new URL(httpUrl);

            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");

            connection.setConnectTimeout(15000);

            connection.setReadTimeout(60000);

            connection.setDoOutput(true);

            connection.setDoInput(true);

            connection.setRequestProperty("Content-Type", "application/json");

            os = connection.getOutputStream();

            os.write(param.getBytes());

            if (connection.getResponseCode() == 200) {

                is = connection.getInputStream();

                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                StringBuffer sbf = new StringBuffer();
                String temp = null;

                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            connection.disconnect();
        }
        return result;
    }

}
