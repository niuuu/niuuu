/**
 * 
 */
package com.app.onenet.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

/**Url工具类
 * @author niu
 * @version 1.0
 * @date 2011-12-30
 */
public class UrlHelper {
	
	/**
	 * url编码
	 * @param str
	 * @return
	 */
	public static final String encodeURL(String str) {
		try {
			return URLEncoder.encode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * url解码
	 * @param str
	 * @return
	 */
	public static final String decodeURL(String str) {
		try {
			return URLDecoder.decode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 拼接地址栏属性 http://localhost?a=1&b=2
	 * @param url 
	 * @param param
	 * @param value
	 * @return
	 */
	public static  final String addParam(String url,String param,String value){
		StringBuffer urlBuffer=new StringBuffer();
		if(url!=null&&!"".equals(url)){
			//判断
			if(url.indexOf("?")!=-1){
				urlBuffer.append(url).append("&").append(param).append("=").append(value);
			}
			else{
				urlBuffer.append(url).append("?").append(param).append("=").append(value);
			}
		}
		return urlBuffer.toString();
	}
	/**
	 * 添加parameters到url上
	 * @param url
	 * @param parameters
	 * @return
	 */
	public static  final String addParam(String url,Map<String, String> parameters){
		StringBuffer sb=new StringBuffer();
		if(url!=null&&!"".equals(url)){
			//判断
			if(url.indexOf("?")!=-1){
				sb.append(url).append("&").append(generatorParamString(parameters));
			}
			else{
				sb.append(url).append("?").append(generatorParamString(parameters));
			}
		}
		return sb.toString();
	}
	/**
	 * 组装parameters为字符串a=1&b=2&c.....
	 * @param parameters
	 * @return
	 */
	 public static String generatorParamString(Map<String, String> parameters) {
	        StringBuffer params = new StringBuffer();
	        if (parameters != null) {
	            for (Iterator<String> iter = parameters.keySet().iterator(); iter.hasNext();) {
	                String name = iter.next();
	                String value = parameters.get(name);
	                params.append(name + "=");
	                try {
	                    params.append(URLEncoder.encode(value, "UTF-8"));
	                } catch (UnsupportedEncodingException e) {
	                    throw new RuntimeException(e.getMessage(), e);
	                } catch (Exception e) {
	                    String message = String.format("'%s'='%s'", name, value);
	                    throw new RuntimeException(message, e);
	                }
	                if (iter.hasNext()) params.append("&");
	            }
	        }
	        return params.toString();
	    }
}
