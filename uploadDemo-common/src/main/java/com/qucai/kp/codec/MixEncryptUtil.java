package com.qucai.kp.codec;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.qucai.kp.tool.JsonTool;


public class MixEncryptUtil {
	
	/**
	 * 请求的安全处理
	 * 
	 * @param rsaPublicKey
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> safeHandle4Req(String rsaPublicKey, Map<String, Object> paramMap) throws Exception {
		Map<String, Object> m = new HashMap<String, Object>();
		
		String desKey = UUID.randomUUID().toString().replaceAll("-", "");
		String paramJson = JsonTool.genByFastJson(paramMap);
		
		// rsa公钥加密deskey
		String encKey = RSAUtil.encryptByPublicKey(desKey, rsaPublicKey);
		// des加密明文
		String encData= DESUtil.encrypt(paramJson, desKey);
		
		m.put("reqToken", encKey);
		m.put("reqData", encData);
		
		return m;
	}
	
	/**
	 * 响应的安全处理
	 * 
	 * @param rsaPrivateKey
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> safeHandle4Resp(String rsaPrivateKey, Map<String, Object> dataMap) throws Exception {
		Map<String, Object> m = new HashMap<String, Object>();
		
		String desKey = UUID.randomUUID().toString().replaceAll("-", "");
		String dataJson = JsonTool.genByFastJson(dataMap);
		
		// rsa私钥加密deskey
		String encKey = RSAUtil.encryptByPrivateKey(desKey, rsaPrivateKey);
		// des加密明文
		String encData= DESUtil.encrypt(dataJson, desKey);
		// rsa私钥签名
		String sign = RSAUtil.sign(encData.getBytes(), rsaPrivateKey);
		
		m.put("respToken", encKey);
		m.put("respData", encData);
		m.put("respSign", sign);
		
		return m;
		
	}
	
}
