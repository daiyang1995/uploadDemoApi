package com.qucai.kp.tool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;

public class FileUtils {

    /**
     * 通过url 获取byte
     * @author yhy 2018年1月3日
     * @param downFileUrl
     * @return
     */
    @SuppressWarnings("static-access")
    public static String getBase64FromUrl(String downFileUrl) {
        try {
            URL url = new URL(downFileUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时间为3秒  
            conn.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误  
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            //得到输入流  
            return new Base64().encodeBase64String(readInputStream(conn.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过url 获取byte
     * @author yhy 2018年1月3日
     * @param downFileUrl
     * @return
     */
    public static byte[] getByteFromUrl(String downFileUrl) {
        try {
            URL url = new URL(downFileUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时间为3秒  
            conn.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误  
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            //得到输入流  
            return readInputStream(conn.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /** 
     * 从输入流中获取字节数组 
     * @param inputStream 
     * @return 
     * @throws IOException 
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

}
