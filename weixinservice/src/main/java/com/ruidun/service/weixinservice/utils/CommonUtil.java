package com.ruidun.service.weixinservice.utils;







import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

public class CommonUtil {
    public  static  String httpsRequest(String requestUrl,String requestMethod,String outputStr ){
        StringBuffer buffer =null;
        try{
            SSLContext sslContext=SSLContext.getInstance("SSL","SunJSSE");
            TrustManager[] tm={new MyX509TrustManager()};
            sslContext.init(null,tm,new java.security.SecureRandom());
            SSLSocketFactory ssf=sslContext.getSocketFactory();

            URL url=new URL(requestUrl);
            HttpsURLConnection conn=(HttpsURLConnection)url.openConnection();
            conn.setRequestMethod(requestMethod);
            conn.setSSLSocketFactory(ssf);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();

            if (null !=outputStr){
                OutputStream os =conn.getOutputStream();
                os.write(outputStr.getBytes("utf-8"));
                os.close();
            }

            InputStream is=conn.getInputStream();
            InputStreamReader isr=new InputStreamReader(is,"utf-8");
            BufferedReader br=new BufferedReader(isr);
            buffer=new StringBuffer();
            String line=null;
            while ((line=br.readLine()) !=null){
                buffer.append(line);
            }

        }catch (Exception o){

        }

        return buffer.toString();
    }


}
