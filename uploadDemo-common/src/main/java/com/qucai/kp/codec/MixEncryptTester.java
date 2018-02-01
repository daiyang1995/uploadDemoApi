package com.qucai.kp.codec;

import java.util.UUID;


public class MixEncryptTester {
	
	static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCux3pjVmIvLO4evzYcKHPtw6J0Ya1xx+U3/PbP3WwTpcbqgMTqUbkRNGyDA5PZI+hqRn51iK9Byg5nr0T9nt/WZ882b/fneBw1wRT8F0IbCXVWVOOIOo70HZryewqVFjD5Kmv7kFCopiVl9/IsE3kuENzsLJJrM4Jda1PVkg71YwIDAQAB";
    static String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAK7HemNWYi8s7h6/Nhwoc+3DonRhrXHH5Tf89s/dbBOlxuqAxOpRuRE0bIMDk9kj6GpGfnWIr0HKDmevRP2e39ZnzzZv9+d4HDXBFPwXQhsJdVZU44g6jvQdmvJ7CpUWMPkqa/uQUKimJWX38iwTeS4Q3Owskmszgl1rU9WSDvVjAgMBAAECgYAFX668pShJqQj9OwXP4dKGqfAxwdsSV4INsaJpC7bJ99vg0+q6BZ86DzGS6aY/E+KOefJjbLtuu2ar+3CUl7DsGpZNK4kB9np1ShdaPU4ArVOb/0ULbEMk2/CruVN0q+/DtulOT3Q69x571l7ENn0p1XVsJ9nrhuNQ5Z5Xq0eiuQJBAO3E6omF/CeObNWOejFZVpL6LByu6Tytwne489WSkaAipdJws17OBJTnuXR9V2r22n91dBQqLIOpRn/ifyaVqF0CQQC8LieV3+D2TAEBS8UFxG8sH0TJq1J4aHvVNDylUaVE9dmL8PaIV4TE9KCW8eXnegcBrmkRqgHbEGHixeoYuji/AkEAvbsMjIDmHYqBPkhbKVXox4/p81l/tHPTYkWsDRjQK8B6IdrcxyIOdeQTEOmUAD7LTDolTpH4w3coUpQicl+p4QJADLhZ+KmPfVaZ4sqKYN9sXGEotzEbfAQkCr9jMhGYyFaOBAWfxIuV7/JRQKLNVZ5MBvMRKx6AzZwH4dpa/pzn5QJBAIpkTtETHNLAWuYCmeRM0iZTsEhsAiQkSvUZ+EmUg9RmFp/449FhEWx3FEN7XDmMai3785/eAZtY5LWBlO6Y+GI=";

	public static void main(String[] args) throws Exception {
		
		// -------------------------------客户端请求---------------------------------
		
		clientRequest();
		
		// ----------------------------------------------------------------------
		
		// -------------------------------服务端响应---------------------------------
		
		serverResponse();
		
		// ----------------------------------------------------------------------
		
	}

	/**
	 * 
	 * 客户端生成deskey，并用其加密请求参数，再使用rsa公钥加密deskey，连同加密后的请求参数一起发送到服务端<br>
	 * 服务端使用rsa私钥解密deskey，再以此解密请求参数<br>
	 * 要求：每次请求的deskey都需要重新生成<br>
	 * 
	 * @throws Exception 
	 * 
	 */
	private static void clientRequest() throws Exception {
		
		String desKey = UUID.randomUUID().toString().replaceAll("-", "");
		String str = "param1=你好hello&param2=xxoo";
		
		System.out.println("desKey:" + desKey + " str:" + str);
		
		// des加密明文
		String encData= DESUtil.encrypt(str, desKey);
		// rsa公钥加密deskey
		String encKey = RSAUtil.encryptByPublicKey(desKey, publicKey);
		
		System.out.println("encData:" + encData + " encKey:" + encKey);
		
		// rsa私钥解密deskey
		String decKey = RSAUtil.decryptByPrivateKey(encKey, privateKey);
		// des解密密文
		String decData= DESUtil.decrypt(encData, desKey);
		
		System.out.println("decData:" + decData + " decKey:" + decKey);
	}
	
	/**
	 * 
	 * 服务端完成处理，生成返回json字符串<br>
	 * 服务端生成deskey，并用其加密返回结果，再使用rsa私钥加密deskey，并对生成的密文进行rsa签名，连同加密后的返回结果一起发送到客户端<br>
	 * 客户端使用rsa公钥验证签名正确性，通过后，使用公钥解密deskey，再以此解密返回结果<br>
	 * 要求：每次响应的deskey都需要重新生成<br>
	 * 
	 * @throws Exception 
	 * 
	 */
	private static void serverResponse() throws Exception {
		String desKey = UUID.randomUUID().toString().replaceAll("-", "");
		String jsonStr = "{reg=0, msg=\"成功\"}";
		
		System.out.println("desKey:" + desKey + " jsonStr:" + jsonStr);

		// des加密明文
		String encData= DESUtil.encrypt(jsonStr, desKey);
		// rsa私钥加密deskey
		String encKey = RSAUtil.encryptByPrivateKey(desKey, privateKey);
		// rsa私钥签名
		String sign = RSAUtil.sign(encData.getBytes(), privateKey);
		
		System.out.println("encData:" + encData + " encKey:" + encKey + " sign:" + sign);
		
		// 公钥验证签名
		boolean rs = RSAUtil.verify(encData.getBytes(), publicKey, sign);
		if(rs){
			// rsa公钥解密deskey
			String decKey = RSAUtil.decryptByPublicKey(encKey, publicKey);
			// des解密密文
			String decData= DESUtil.decrypt(encData, decKey);
			
			System.out.println("decData:" + decData + " decKey:" + decKey);
		}
	}
	
}
