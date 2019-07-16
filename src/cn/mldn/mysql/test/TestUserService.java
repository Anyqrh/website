package cn.mldn.mysql.test;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.alibaba.fastjson.JSONObject;

import cn.mldn.mysql.vo.User;

public class TestUserService {
	public static void main(String [] args) {
		System.out.println(getJSONObject());
		
	}
	public static String getJSONObject() {
		JSONObject obj = null;
		try {
		obj = new JSONObject();
		obj.put("userName","ÕÅÈý");
		}catch(Exception e) {
			System.out.println("ss");
			e.printStackTrace();
		}
		
		System.out.println(obj.toString());
		return obj.toString();
	}
	@RenderMapping(value = "/ss")
public @ResponseBody String requestJson(@RequestBody User user) {
	return null;
		
	}
}

class JsonTest {
	
	public @ResponseBody String requestJson(@RequestBody User user) {
		return null;
		
	}
}
