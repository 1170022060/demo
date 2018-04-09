package com.demo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.pojo.Message;
import com.demo.pojo.baseMsssage;
import com.demo.util.msgUtil;

@RestController
@RequestMapping("/wx")
public class hello {
	@PostMapping("/")
	public void msg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();

		String str = null;

		try {

			// 将request请求，传到Message工具类的转换方法中，返回接收到的Map对象

			Map<String, String> map = msgUtil.xmlToMap(request);

			// 从集合中，获取XML各个节点的内容

			String toUserName = map.get("ToUserName");

			String FromUserName = map.get("FromUserName");

			String CreateTime = map.get("CreateTime");

			String MsgType = map.get("MsgType");

			String Content = map.get("Content");

			String MsgId = map.get("MsgId");

			if (MsgType.equals(msgUtil.MESSAGE_TEXT)) {// 判断消息类型是否是文本消息(text)
				switch (Content) {
				case "教育":
					str = msgUtil.initNewsMessage(toUserName, FromUserName);
					break;
				case "音乐":
					str = msgUtil.initMusicMessage(toUserName, FromUserName);
					break;

				default:
					str = msgUtil.initText(toUserName, FromUserName, Content);
					break;
				}
				//str = msgUtil.initNewsMessage(ToUserName, FromUserName);
				
			}else if(MsgType.equals(msgUtil.MESSAGE_EVENT)) {//判断是否是事件类型
				String eventType = map.get("Event");//
				if(eventType.equals(msgUtil.MESSAGE_SUBSCRIBE)) {//关注事件
					str = msgUtil.initText(toUserName, FromUserName, msgUtil.menuText());
				}
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.print(str);
		out.close();

	}
	
	@GetMapping("/hello")
	public baseMsssage sayhello() {
		baseMsssage  bm = new baseMsssage();
		bm.setFromUserName("陈卫国");
		bm.setToUserName("原海忠");
		bm.setCreateTime(System.currentTimeMillis());
		bm.setMsgType("json");
		return bm;
	}
	
	@GetMapping("/name")
	public String getName() {
		return "张三";
	}
}
