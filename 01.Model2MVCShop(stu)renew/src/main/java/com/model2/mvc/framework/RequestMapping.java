package com.model2.mvc.framework;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class RequestMapping {
	
	private static RequestMapping requestMapping;
	private Map<String, Action> map;
	private Properties properties;
	
	private RequestMapping(String resources) {
		map = new HashMap<String, Action>();
		InputStream in = null;
		try{
			in = getClass().getClassLoader().getResourceAsStream(resources);
			properties = new Properties();
			properties.load(in);
		}catch(Exception ex){
			System.out.println(ex);
			throw new RuntimeException("actionmapping.properties 파일 로딩 실패 :"  + ex);
		}finally{
			if(in != null){
				try{ in.close(); } catch(Exception ex){}
			}
		}
	}
	//여기 파싱하는 논리와 놀자
	
	
	public synchronized static RequestMapping getInstance(String resources){
		if(requestMapping == null){
			requestMapping = new RequestMapping(resources);
		}
		return requestMapping;
	}
//닫으라 하심. 인사팀장이 여러 명일 수 없으니 한 명만 만드는 테크닉
	
	public Action getAction(String path){
		Action action = map.get(path);
		if(action == null){
			String className = properties.getProperty(path);
			System.out.println("prop : " + properties);
			System.out.println("path : " + path);			
			System.out.println("className : " + className);
			className = className.trim();
			try{
				Class c = Class.forName(className);
				Object obj = c.newInstance();
				if(obj instanceof Action){
					map.put(path, (Action)obj);
					action = (Action)obj;
				}else{
					throw new ClassCastException("Class형변환시 오류 발생  ");
				}
			}catch(Exception ex){
				System.out.println(ex);
				throw new RuntimeException("Action정보를 구하는 도중 오류 발생 : " + ex);
			}
		}
		return action;
	}


}