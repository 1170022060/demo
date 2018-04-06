package com.demo.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.demo.pojo.Message;
import com.demo.pojo.MusicMessage;
import com.demo.pojo.News;
import com.demo.pojo.NewsMessage;
import com.thoughtworks.xstream.XStream;

public class msgUtil {

	public static final String MESSAGE_TEXT = "text";

	public static final String MESSAGE_IMAGE = "image";

	public static final String MESSAGE_VOICE = "voice";

	public static final String MESSAGE_VIDEO = "video";

	public static final String MESSAGE_SHORTVIDEO = "shortvideo";

	public static final String MESSAGE_LINK = "link";

	public static final String MESSAGE_LOCATION = "location";

	public static final String MESSAGE_EVENT = "event";

	public static final String MESSAGE_SUBSCRIBE = "subscribe";

	public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";

	public static final String MESSAGE_CLICK = "CLICK";

	public static final String MESSAGE_VIEW = "VIEW";

	public static final String MESSAGE_SCAN = "SCAN";
	public static final String MESSAGE_NEWS = "news";
	public static final String MESSAGE_MUSIC = "music";

	// xml转map
	public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		// 从request获取输入流
		InputStream inputStream = request.getInputStream();
		Document doc = reader.read(inputStream);
		Element rootElement = doc.getRootElement();
		List<Element> elements = rootElement.elements();
		for (Element e : elements) {
			map.put(e.getName(), e.getText());
		}
		inputStream.close();
		return map;

	}

	// 对象转xml
	public static String objectToXml(Message message) {

		XStream stream = new XStream();
		stream.alias("xml", message.getClass());
		return stream.toXML(message);

	}

	// 对象转xml
	public static String newsMessageToXml(NewsMessage message) {

		XStream stream = new XStream();
		stream.alias("xml", message.getClass());
		stream.alias("item", new News().getClass());
		return stream.toXML(message);

	}

	// 音乐对象转xml
	public static String musicMessageToXml(MusicMessage message) {

		XStream stream = new XStream();
		stream.alias("xml", message.getClass());
		return stream.toXML(message);

	}

	// 菜单消息
	public static String menuText() {

		StringBuffer sb = new StringBuffer();

		sb.append("欢迎关注史上最帅公众号，请选择:\n\n");

		sb.append("1、老陈讲故事。\n");

		sb.append("2、老陈看电影。\n\n");

		sb.append("回复？调出主菜单。\n\n");

		return sb.toString();

	}

	/*
	 * public static String msgInFo(String toUSerName,String fromUserName,String
	 * content) { Message message = new Message();
	 * 
	 * message.setFromUserName(toUSerName);// 原来【接收消息用户】变为回复时【发送消息用户】
	 * 
	 * message.setToUserName(fromUserName);
	 * 
	 * message.setMsgType(MESSAGE_TEXT);
	 * 
	 * message.setCreateTime(new Date().getTime());// 创建当前时间为消息时间
	 * 
	 * message.setContent(content);
	 * 
	 * return msgUtil.objectToXml(message); }
	 */
	public static String initText(String toUSerName, String fromUserName, String content) {

		Message text = new Message();

		text.setFromUserName(toUSerName);

		text.setToUserName(fromUserName);

		text.setMsgType(MESSAGE_TEXT);

		text.setCreateTime(System.currentTimeMillis());

		text.setContent(content);

		return msgUtil.objectToXml(text);

	}

	public static String initNewsMessage(String toUSerName, String fromUserName) {

		List newsList = new ArrayList();

		NewsMessage newsMessage = new NewsMessage();

		News newsItem = new News();

		newsItem.setTitle("欢迎来到杰瑞教育");

		newsItem.setDescription("杰瑞教育，中国高端互联网人才培训领导品牌！");

		newsItem.setPicUrl("http://www.jredu100.com/statics/images/index/top/logo.png");

		newsItem.setUrl("www.jredu100.com");

		newsList.add(newsItem);

		// 组装图文消息相关信息

		newsMessage.setToUserName(fromUserName);

		newsMessage.setFromUserName(toUSerName);

		newsMessage.setCreateTime(System.currentTimeMillis());

		newsMessage.setMsgType(MESSAGE_NEWS);

		newsMessage.setArticles(newsList);

		newsMessage.setArticleCount(newsList.size());

		// 调用newsMessageToXml将图文消息转化为XML结构并返回

		return msgUtil.newsMessageToXml(newsMessage);

	}
	
	public static String initMusicMessage(String toUSerName, String fromUserName) {
		
		MusicMessage musicMessage = new MusicMessage();
		musicMessage.setToUserName(fromUserName);
		musicMessage.setFromUserName(toUSerName);
		musicMessage.setCreateTime(System.currentTimeMillis());
		musicMessage.setDescription("好歌");
		musicMessage.setHQMusicUrl("http://ws.stream.qqmusic.qq.com/C100002qU5aY3Qu24y.m4a?fromtag=0");
		musicMessage.setMsgType(MESSAGE_MUSIC);
		musicMessage.setThumbMediaId("dd");
		musicMessage.setMusicURL("http://ws.stream.qqmusic.qq.com/C100002qU5aY3Qu24y.m4a?fromtag=0");
		return msgUtil.musicMessageToXml(musicMessage);


	}

}
