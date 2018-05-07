package org.zjr.weixin.util;


import java.security.MessageDigest;
import java.util.Arrays;

public class SignUtil {
	private static String tooken="zjrzjrzjr";
	public static boolean checkSignature(String signature,String timestamp,String nonce){

		String[] arr = {tooken,timestamp,nonce};

		Arrays.sort(arr);

		StringBuffer sb = new StringBuffer();
		for(String s : arr){
			sb.append(s);
		}

		String temp = getSha1(sb.toString());

		return temp.equals(signature);	
	}
	
	public static String getSha1(String str){
        if(str==null||str.length()==0){
            return null;
        }
        char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9',
                'a','b','c','d','e','f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j*2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];      
            }
            return new String(buf);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }
//	public static boolean checkSignature(String signature,String timestamp,String nonce) {
//		boolean result =false;
//		String[] array=new String[]{tooken,timestamp,nonce};
//	
//		String str=array[0].concat(array[1]).concat(array[2]);
//		String sha1Str=null;
//		try{
//			MessageDigest md=MessageDigest.getInstance("SHA-1");
//			byte[] digest=md.digest(str.getBytes());
//			sha1Str=byte2str(digest);
//		}catch (Exception e) {
//			
//		}
//		if (sha1Str !=null&&sha1Str.equals(signature)) {
//			result=true;
//		}
//		return result;
//	}
//	
//	public static String byte2str(byte[] array) {
//		StringBuffer hexstr=new StringBuffer();
//		String shaHex="";
//		for (int i=0;i<array.length;i++) {
//			shaHex=Integer.toHexString(array[i] & 0xFF);
//			if (shaHex.length()<2) {
//				hexstr.append(0);
//			}
//			hexstr.append(shaHex);
//		}
//		return hexstr.toString();
//	}
	
}
