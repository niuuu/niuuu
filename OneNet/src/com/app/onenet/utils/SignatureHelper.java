/**
 * 
 */
package com.app.onenet.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import com.app.onenet.constant.Constants;

/**签名工具类
 * @author niu
 * @version 1.0
 * @date 2012-2-3
 */
public class SignatureHelper {

	/**
	 * 拼接参数签名
	 * @param params
	 * @return
	 */
	public static TreeMap<String, String> sigParams(
			TreeMap<String, String> params) {
		StringBuffer sb = new StringBuffer();
		for (Iterator<Map.Entry<String, String>> iterator = params.entrySet()
				.iterator(); iterator.hasNext();) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator
					.next();
			sb.append(entry.getKey());
			sb.append("=");
			sb.append(entry.getValue());
		}
		// 签名私钥
		sb.append(Constants.APP_SECRET);
		params.put("sign", MD5Utils.md5(sb.toString()));
		return params;
	}

	/**
	 * 参数签名值
	 * @param params
	 * @return
	 */
	public static String signText(TreeMap<String, String> params) {
		StringBuffer sb = new StringBuffer();
		for (Iterator<Map.Entry<String, String>> iterator = params.entrySet()
				.iterator(); iterator.hasNext();) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator
					.next();
			String key = entry.getKey();
			if (key != "sign" && !"sign".equals(key)) {
				sb.append(key);
				sb.append("=");
				sb.append(entry.getValue());
			}

		}
		sb.append(Constants.APP_SECRET);
		return MD5Utils.md5(sb.toString());
	}

	/**
	 * 校验参数签名
	 * @param params
	 * @return
	 */
	public static boolean verifyParams(TreeMap<String, String> params) {
		if (!params.containsKey("sign"))
			return false;
		String signtext1 = signText(params);
		String sign = params.get("sign");
		return sign.equals(signtext1);
	}
}
