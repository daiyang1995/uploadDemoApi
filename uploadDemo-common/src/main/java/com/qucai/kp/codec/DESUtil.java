package com.qucai.kp.codec;

import java.security.SecureRandom;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

public class DESUtil {

	public static String encrypt(String data, String desKey) throws Exception {
		return Base64.encodeBase64String(encrypt(data.getBytes(), desKey));
	}

	public static String decrypt(String data, String desKey) throws Exception {
		return new String(decrypt(Base64.decodeBase64(data), desKey));
	}

	/**
	 * 加密
	 * 
	 * @param data
	 * @param desKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, String desKey) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom random = new SecureRandom();
		DESKeySpec keySeed = new DESKeySpec(desKey.getBytes());
		// 创建一个密匙工厂，然后用它把DESKeySpec转换成
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey securekey = keyFactory.generateSecret(keySeed);
		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance("DES");
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
		// 执行加密操作
		return cipher.doFinal(data);
	}

	/**
	 * 解密
	 * 
	 * @param src
	 * @param desKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] src, String desKey) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom random = new SecureRandom();
		// 创建一个DESKeySpec对象
		DESKeySpec keySeed = new DESKeySpec(desKey.getBytes());
		// 创建一个密匙工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		// 将DESKeySpec对象转换成SecretKey对象
		SecretKey securekey = keyFactory.generateSecret(keySeed);
		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance("DES");
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, random);
		// 执行解密操作
		return cipher.doFinal(src);
	}

	public static void main(String[] args) throws Exception {
		String desKey = UUID.randomUUID().toString().replaceAll("-", "");
		String str = "你好hello";
		String rs = DESUtil.encrypt(str, desKey);
		System.out.println(rs);
		System.out.println(DESUtil.decrypt(rs, desKey));
	}

}
