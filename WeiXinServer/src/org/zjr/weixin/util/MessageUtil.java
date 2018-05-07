package org.zjr.weixin.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.zjr.weixin.message.Article;
import org.zjr.weixin.message.ImageMessage;
import org.zjr.weixin.message.MusicMessage;
import org.zjr.weixin.message.NewsMessage;
import org.zjr.weixin.message.TextMessage;
import org.zjr.weixin.message.VideoMessage;
import org.zjr.weixin.message.VoiceMessage;

import com.thoughtworks.xstream.XStream;

public class MessageUtil {
    public static final String REQ_MESSAGE_TYPE_TEXT="text";
    public static final String REQ_MESSAGE_TYPE_IMAGE="image";
    public static final String REQ_MESSAGE_TYPE_VOICE="voice";
    public static final String REQ_MESSAGE_TYPE_VIDEO="video";
    public static final String REQ_MESSAGE_TYPE_LOCATION="location";
    public static final String REQ_MESSAGE_TYPE_LINK="link";
    
    public static final String RESP_MESSAGE_TYPE_EVENT="event";
    
    public static final String RESP_MESSAGE_TYPE_TEXT="text";
    public static final String RESP_MESSAGE_TYPE_IMAGE="image";
    public static final String RESP_MESSAGE_TYPE_VOICE="voice";
    public static final String RESP_MESSAGE_TYPE_VIDEO="video";
    public static final String RESP_MESSAGE_TYPE_MUSIC="music";
    public static final String RESP_MESSAGE_TYPE_NEWS="news";
    
    public static final String EVENT_TYPE_SUBSCRIBE="subscribe";
    public static final String EVENT_TYPE_UNSUBSCRIBE="unsubscribe";
    public static final String EVENT_TYPE_CLICK="CLICK";
    public static final String EVENT_TYPE_QCODE="scancode_push";
    
    
    public static HashMap<String,String> parseXML(HttpServletRequest request) throws Exception {
    	HashMap<String,String>map=new HashMap<String, String>();
    	SAXReader reader = new SAXReader();
		Document doc =  reader.read(request.getInputStream());
		Element root = doc.getRootElement();
		recursiveParseXML(root,map);
		return map;
	}
    private static void  recursiveParseXML(Element root,HashMap<String,String>map) {
    	List<Element>elementList=root.elements();
    	if (elementList.size()==0) {
			map.put(root.getName(),root.getTextTrim());
		}else {
			for(Element e:elementList){
				recursiveParseXML(e,map);
			}
		}
		
	}
    

    
    public static String messageToXML(TextMessage textMessage) {
    	XStream xstream = new XStream();
    	xstream.alias("xml", TextMessage.class);
    	return xstream.toXML(textMessage);
	}
    public static String messageToXML(ImageMessage imageMessage) {
    	XStream xstream = new XStream();
    	xstream.alias("xml", ImageMessage.class);
    	return xstream.toXML(imageMessage);
	}
    public  static String messageToXML(MusicMessage musicMessage) {
    	XStream xstream = new XStream();
    	xstream.alias("xml", MusicMessage.class);
    	return xstream.toXML(musicMessage);
	}
    public static String messageToXML(NewsMessage newsMessage) {
    	XStream xstream = new XStream();
    	xstream.alias("xml", NewsMessage.class);
    	xstream.alias("item", Article.class);
    	return xstream.toXML(newsMessage);
	}
    public static String messageToXML(VideoMessage videoMessage) {
    	XStream xstream = new XStream();
    	xstream.alias("xml", VideoMessage.class);
    	return xstream.toXML(videoMessage);
	}
    public static String messageToXML(VoiceMessage voiceMessage) {
    	XStream xstream = new XStream();
    	xstream.alias("xml", TextMessage.class);
    	return xstream.toXML(voiceMessage);
	}


	public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException{
		Map<String, String> map = new HashMap<String, String>();

		SAXReader reader = new SAXReader();
		InputStream is = request.getInputStream();
		Document doc =  reader.read(is);
		Element root = doc.getRootElement();
		List<Element> list = root.elements();
		for (Element e : list) {
			map.put(e.getName(), e.getText());
		}
		is.close();
		return map;
	}

	public static String objectToXml(TextMessage message){
		XStream xs = new XStream();

		xs.alias("xml", TextMessage.class);
		return xs.toXML(message);
	}
}
