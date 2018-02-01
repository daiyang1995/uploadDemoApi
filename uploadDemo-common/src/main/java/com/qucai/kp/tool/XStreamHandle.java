package com.qucai.kp.tool;

import java.io.StringWriter;

import org.apache.commons.lang3.StringUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

public class XStreamHandle {
    /**
     * toxml not format
     * @author yhy 2017年5月23日
     * @param obj
     * @param headCharset
     * @return
     */
    public static String toXml(Object obj, String headCharset) {
        String charset = "utf8";
        String top = "<?xml version=\"1.0\" encoding=\"" + headCharset + "\"?>";
        XStream xstream = new XStream(new DomDriver(charset, new XmlFriendlyNameCoder("__", "_")));
        xstream.processAnnotations(obj.getClass()); // 识别obj类中的注解
        // 以压缩的方式输出XML
        StringWriter sw = new StringWriter();
        xstream.marshal(obj, new CompactWriter(sw));
        return top + sw.toString().replaceAll("，", ",");
    }

    /**
     * toxml format
     * @author yhy 2017年5月23日
     * @param obj
     * @param headCharset
     * @return
     */
    public static String toXmlFormat(Object obj, String headCharset) {
        String charset = "utf8";
        String top = "<?xml version=\"1.0\" encoding=\"" + headCharset + "\"?> \n";
        XStream xstream = new XStream(new DomDriver(charset, new XmlFriendlyNameCoder("__", "_")));
        xstream.processAnnotations(obj.getClass()); // 识别obj类中的注解
        // 以格式化的方式输出XML
        return top + xstream.toXML(obj).replaceAll("，", ",");
    }

    public static <T> T toBean(String xmlStr, Class<T> cls) {
        if (StringUtils.isBlank(xmlStr)) {
            return null;
        }
        XStream xstream = new XStream(new DomDriver(null, new XmlFriendlyNameCoder("__", "_")));
        xstream.processAnnotations(cls);
        @SuppressWarnings("unchecked")
        T t = (T) xstream.fromXML(xmlStr);
        return t;
    }

}