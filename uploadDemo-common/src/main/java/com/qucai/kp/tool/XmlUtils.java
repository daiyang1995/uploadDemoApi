package com.qucai.kp.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * XML文件转换工具类
 * 
 * @class XmlCoreUtils.java
 * 
 * @author XL.Pei
 * 
 * @date 2017年2月27日-上午11:25:12
 *
 */
public class XmlUtils {

	/**
	 * Object转换为XML格式字符串
	 * 
	 * @param object
	 * @return
	 */
	public static String objectToXml(Object object) {
		XStream xStream = new XStream();
		xStream.processAnnotations(object.getClass());
		return xStream.toXML(object);
	}

	/**
	 * Object转换为XML格式文件
	 * 
	 * @param obj
	 *            - 转换对象
	 * @param outPath
	 *            -文件绝对路径
	 * @param fileName
	 *            文件名
	 * @return
	 */
	public static Boolean objectToXmlFile(Object obj, String outPath, String fileName) {
		XStream xStream = new XStream();
		xStream.processAnnotations(obj.getClass());
		String filePath = outPath + fileName;
		OutputStream ous = null;
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				file.createNewFile();
			}
			ous = new FileOutputStream(file);
			xStream.toXML(obj, ous);
			ous.flush();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (ous != null)
				try {
					ous.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return true;
	}

	/**
	 * XML字符串转换为Bean对象
	 * 
	 * @param xmlStr
	 * @param classsObj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T xmlStrToBean(String xmlStr, Class<T> classsObj) {
		XStream xstream = new XStream(new DomDriver());
		xstream.processAnnotations(classsObj);
		T obj = (T) xstream.fromXML(xmlStr);
		return obj;
	}

	/**
	 * XML格式文件转换为Bean对象
	 * 
	 * @param realPath
	 * @param fileName
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T xmlFileToBean(String realPath, String fileName, Class<T> cls) throws Exception {
		String filePath = realPath + fileName;
		InputStream ins = null;
		XStream xstream = new XStream(new DomDriver("UTF-8"));
		xstream.processAnnotations(cls);
		T obj = null;
		try {
			ins = new FileInputStream(new File(filePath));
			obj = (T) xstream.fromXML(ins);
		} catch (Exception e) {
			throw new Exception("解析{" + filePath + "}文件失败！", e);
		} finally {
			if (ins != null)
				ins.close();
		}
		return obj;
	}

}
