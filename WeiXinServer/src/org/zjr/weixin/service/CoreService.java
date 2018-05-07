package org.zjr.weixin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zjr.weixin.message.Article;
import org.zjr.weixin.message.Music;
import org.zjr.weixin.message.MusicMessage;
import org.zjr.weixin.message.NewsMessage;
import org.zjr.weixin.message.TextMessage;
import org.zjr.weixin.util.HttpClient;
import org.zjr.weixin.util.MessageUtil;

import net.sf.json.JSONObject;


public class CoreService {
		public static String processRequset(HttpServletRequest request) {
			 String  respXML=null;
			TextMessage tm=new TextMessage();
			try {
			HashMap<String, String>requestMap=	MessageUtil.parseXML(request);
			String fromUserName=requestMap.get("FromUserName");
			String toUserName=requestMap.get("ToUserName");
			String msgType=requestMap.get("MsgType");
			tm.setFromUserName(toUserName);
			tm.setToUserName(fromUserName);
			tm.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			tm.setCreateTime(new Date().getTime());
		
			if (MessageUtil.REQ_MESSAGE_TYPE_TEXT.equals(msgType)) {
				String content=requestMap.get("Content");
//				MySQLUtil.saveTextMessage(fromUserName, content);
//				tm.setContent("SAE");
				if ("3".equals(content)) {
					tm.setContent("<a href=\"http://www.baidu.com\">baidu</a>");
					respXML = MessageUtil.messageToXML(tm);
				}else if ("gequ".equals(content)) {
					Music music= new Music();
					music.setTitle("zhoubichang");
					music.setDescription("zuimeideqidai");
					music.setMusicUrl("http://www.shouyifenxi.com/WeiXinServer/Music/1517021801.mp3");
					music.setHQMusicUrl("http://www.shouyifenxi.com/WeiXinServer/Music/1517021801.mp3");
					MusicMessage musicMessage = new MusicMessage();
					musicMessage.setToUserName(fromUserName);
					musicMessage.setFromUserName(toUserName);
					musicMessage.setCreateTime(new Date().getTime());
					musicMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_MUSIC);
					musicMessage.setMusic(music);
					respXML = MessageUtil.messageToXML(musicMessage);
				}else if ("4".equals(content)) {
					Article article=new Article();
					article.setTitle("gequ");
					article.setDescription("zuimeideqidai");
					article.setPicUrl("");
					article.setUrl("http://www.shouyifenxi.com/WeiXinServer/Music/1517021801.mp3");
					List<Article>articlelist=new ArrayList<Article>();
					articlelist.add(article);
					NewsMessage newsMessage = new NewsMessage();
					newsMessage.setToUserName(fromUserName);
					newsMessage.setFromUserName(toUserName);
					newsMessage.setCreateTime(new Date().getTime());
					newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
					newsMessage.setArticleCount(articlelist.size());
					newsMessage.setArticles(articlelist);
					respXML = MessageUtil.messageToXML(newsMessage);
				}else if ("5".equals(content)) {
					Article article=new Article();
					article.setTitle("gequ");
					article.setDescription("zuimeideqidai");
					article.setPicUrl("http://www.shouyifenxi.com/WeiXinServer/image/image.png");
					article.setUrl("http://www.shouyifenxi.com/WeiXinServer/Music/1517021801.mp3");
					List<Article>articlelist=new ArrayList<Article>();
					articlelist.add(article);
					NewsMessage newsMessage = new NewsMessage();
					newsMessage.setToUserName(fromUserName);
					newsMessage.setFromUserName(toUserName);
					newsMessage.setCreateTime(new Date().getTime());
					newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
					newsMessage.setArticleCount(articlelist.size());
					newsMessage.setArticles(articlelist);
					respXML = MessageUtil.messageToXML(newsMessage);
				}else if ("6".equals(content)) {
					Article article=new Article();
					article.setTitle("gequ");
					article.setDescription("zuimeideqidai");
					article.setPicUrl("http://www.shouyifenxi.com/WeiXinServer/image/image.png");
					article.setUrl("http://www.shouyifenxi.com/WeiXinServer/Music/1517021801.mp3");
					Article article2=new Article();
					article2.setTitle("gequ");
					article2.setDescription("zuimeideqidai");
					article2.setPicUrl("http://www.shouyifenxi.com/WeiXinServer/image/image.png");
					article2.setUrl("http://www.shouyifenxi.com/WeiXinServer/Music/1517021801.mp3");
					Article article3=new Article();
					article3.setTitle("gequ");
					article3.setDescription("zuimeideqidai");
					article3.setPicUrl("http://www.shouyifenxi.com/WeiXinServer/image/image.png");
					article3.setUrl("http://www.shouyifenxi.com/WeiXinServer/Music/1517021801.mp3");
					List<Article>articlelist=new ArrayList<Article>();
					articlelist.add(article);
					articlelist.add(article2);
					articlelist.add(article3);
					NewsMessage newsMessage = new NewsMessage();
					newsMessage.setToUserName(fromUserName);
					newsMessage.setFromUserName(toUserName);
					newsMessage.setCreateTime(new Date().getTime());
					newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
					newsMessage.setArticleCount(articlelist.size());
					newsMessage.setArticles(articlelist);
					respXML = MessageUtil.messageToXML(newsMessage);
				}else if ("2048".equals(content)) {
					tm.setContent("<a href=\"http://www.shouyifenxi.com/WeiXinServer/2048-master/index.html\">2048</a>");
					respXML = MessageUtil.messageToXML(tm);
				}else if("1".equals(content)){
					Article article=new Article();
					article.setTitle("小太阳使用说明");
					article.setDescription("感谢您使用充电小太阳，本文介绍充电小太阳手机扫码及支付的使用指南。");
					article.setPicUrl("http://www.shouyifenxi.com/WeiXinServer/image/useinstruction.png");
					article.setUrl("http://www.shouyifenxi.com/WeiXinServer/Music/1517021801.mp3");
					List<Article>articlelist=new ArrayList<Article>();
					articlelist.add(article);
					NewsMessage newsMessage = new NewsMessage();
					newsMessage.setToUserName(fromUserName);
					newsMessage.setFromUserName(toUserName);
					newsMessage.setCreateTime(new Date().getTime());
					newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
					newsMessage.setArticleCount(articlelist.size());
					newsMessage.setArticles(articlelist);
					respXML = MessageUtil.messageToXML(newsMessage);
				}else if("2".equals(content)){
				
				}else {
					tm.setContent("text");
					respXML = MessageUtil.messageToXML(tm);
				}
			}else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				tm.setContent("picture");
				respXML = MessageUtil.messageToXML(tm);
			}else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				tm.setContent("line");
				respXML = MessageUtil.messageToXML(tm);
			}else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				tm.setContent("location");
				respXML = MessageUtil.messageToXML(tm);
			}else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
				tm.setContent("video");
				respXML = MessageUtil.messageToXML(tm);
			}else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				tm.setContent("voice");
				respXML = MessageUtil.messageToXML(tm);
			}else if (msgType.equals(MessageUtil.RESP_MESSAGE_TYPE_EVENT)) {
				String eventType=requestMap.get("Event");
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					Article article=new Article();
					article.setTitle("小太阳使用说明");
					article.setDescription("感谢您使用充电小太阳，本文介绍充电小太阳手机扫码及支付的使用指南。");
					article.setPicUrl("http://www.shouyifenxi.com/WeiXinServer/image/useinstruction.png");
					article.setUrl("http://www.shouyifenxi.com/WeiXinServer/Music/1517021801.mp3");
					List<Article>articlelist=new ArrayList<Article>();
					articlelist.add(article);
					NewsMessage newsMessage = new NewsMessage();
					newsMessage.setToUserName(fromUserName);
					newsMessage.setFromUserName(toUserName);
					newsMessage.setCreateTime(new Date().getTime());
					newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
					newsMessage.setArticleCount(articlelist.size());
					newsMessage.setArticles(articlelist);
					respXML = MessageUtil.messageToXML(newsMessage);
//					StringBuffer sb = new StringBuffer();
//					sb.append("感谢您关注并使用小太阳，使用说明请回复1。\n");
//					sb.append("特别提醒：\n");
//					sb.append("1、夜间或光线较暗，请打开手机手电筒予以补光。\n");
//					sb.append("2、手机信号较差，操作可能会延迟或无法操作，可以记住站点及插座，到信号满足需求的环境下远程使用。\n");
//					sb.append("3、使用时充电器呗拔下，设备会自动断电。\n\n");
//					sb.append("感谢您的使用。");
//					tm.setContent(sb.toString());
//					respXML = MessageUtil.messageToXML(tm);
				}else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					
				}else if(eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					String eventkey=requestMap.get("EventKey");
					if (eventkey.equals("KEY_SHOW")) {
						
					}
				}else if (eventType.equals(MessageUtil.EVENT_TYPE_QCODE)) {
					String eventkey=requestMap.get("EventKey");
					String ScanResult=requestMap.get("ScanResult");
					if(eventkey.equals("KEY_qcode")) {
						
					}
				}
			}
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return respXML;
			
		}
}
