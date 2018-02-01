package com.qucai.kp.tool;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qucai.kp.codec.DESUtil;
import com.qucai.kp.codec.MixEncryptUtil;
import com.qucai.kp.codec.RSAUtil;

/**
 * 
 * HttpTool中包含多种请求方式，根据需要在此处包装方法
 * 
 * @author owner
 *
 */
public class SafeHttpTool {

	private static Logger logger = LoggerFactory.getLogger(SafeHttpTool.class);

	public static JSONObject callApiReturnJsonObj(boolean debug, String url,
			boolean https, String type, Map<String, Object> headerMap,
			Map<String, Object> paramMap, String publicKey, ApiCallbackDeal callback) throws Exception {
		JSONObject rsJson = JSON.parseObject(callApiReturnJsonStr(debug, url, https, type,
				headerMap, paramMap, publicKey));
		callback.deal(rsJson);
		return rsJson;
	}
	
	public static JSONObject callApiReturnJsonObj(boolean debug, String url,
			boolean https, String type, Map<String, Object> headerMap,
			Map<String, Object> paramMap, String publicKey) throws Exception {
		return JSON.parseObject(callApiReturnJsonStr(debug, url, https, type,
				headerMap, paramMap, publicKey));
	}

	public static String callApiReturnJsonStr(boolean debug, String url,
			boolean https, String type, Map<String, Object> headerMap,
			Map<String, Object> paramMap, String publicKey) throws Exception {
		// 对请求参数进行安全处理
		Map<String, Object> safeReqMap = MixEncryptUtil.safeHandle4Req(
				publicKey, paramMap);

		// 调用接口获取返回结果
		Map<String, Object> rsMap = HttpTool.callApiReturnMap(debug, url,
				type, headerMap, safeReqMap);

		// 固定返回项
		String respToken = String.valueOf(rsMap.get("respToken"));
		String respData = String.valueOf(rsMap.get("respData"));
		String respSign = String.valueOf(rsMap.get("respSign"));

		// 公钥验证签名
		boolean rs = RSAUtil.verify(respData.getBytes(), publicKey,
				respSign);
		if (rs) {
			// rsa公钥解密deskey
			String decKey = RSAUtil
					.decryptByPublicKey(respToken, publicKey);
			// des解密密文
			String decData = DESUtil.decrypt(respData, decKey);

			if (debug) {
				logger.info("decData (json type string):" + decData
						+ " decKey:" + decKey);
			}

			return decData;
		}

		return "";
	}
}
