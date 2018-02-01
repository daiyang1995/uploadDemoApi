package com.qucai.kp.tool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.codec.binary.Base64;

public class SerializeTool {

	public static String serialize2Base64Str(Object o) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(o);
			byte[] byteArray = baos.toByteArray();
			oos.close();
			baos.close();
			return Base64.encodeBase64String(byteArray);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	public static <T> T deserializeFromBase64Str(String base64Str) {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(
					Base64.decodeBase64(base64Str));
			ObjectInputStream ois = new ObjectInputStream(bais);
			T obj = (T) ois.readObject();
			ois.close();
			bais.close();
			return obj;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
