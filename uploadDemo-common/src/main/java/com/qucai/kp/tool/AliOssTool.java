package com.qucai.kp.tool;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.Locale;
import java.util.Properties;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.DownloadFileRequest;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.UploadFileRequest;
import com.aliyuncs.exceptions.ClientException;

public class AliOssTool {

    private static String endPoind;
    private static String accessId;
    private static String accessKey;
    private static String publicDomain;
    private static OSSClient client;

    public static String PUBLIC_BUCKET_ID;
    public static String PRIVATE_BUCKET_ID;

    static {
        InputStream in = AliOssTool.class.getResourceAsStream("/ali4oss.properties");
        try {
            Properties properties = new Properties();
            properties.load(in);

            endPoind = properties.getProperty("oss.endPoind");
            accessId = properties.getProperty("oss.accessId");
            accessKey = properties.getProperty("oss.accessKey");
            publicDomain = properties.getProperty("oss.public.domain");
            PUBLIC_BUCKET_ID = properties.getProperty("oss.bucketId.public");
            PRIVATE_BUCKET_ID = properties.getProperty("oss.bucketId.private");

            client = new OSSClient(endPoind, accessId, accessKey);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 本地文件上传，默认文件类型image/jpeg
     * 
     * @param bucketId
     * @param objectId
     * @param localFile
     * @throws Exception
     */
    public static void uploadByLocalFile(String bucketId, String objectId, File localFile) {
        uploadByLocalFile(bucketId, objectId, localFile, "image/jpeg");
    }

    /**
     * 本地文件上传
     * 
     * @param bucketId
     * @param objectId
     * @param localFile
     * @param contentType
     * @throws Exception
     */
    public static void uploadByLocalFile(String bucketId, String objectId, File localFile, String contentType) {
        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(localFile.length());
        objectMeta.setContentType(contentType);

        client.putObject(bucketId, objectId, localFile, objectMeta);
    }

    /**
     * 数据流上传，默认文件类型image/jpeg
     * 
     * @param bucketId
     * @param objectId
     * @param ins
     * @throws Exception
     */
    public static void uploadByStream(String bucketId, String objectId, InputStream ins) throws Exception {
        uploadByStream(bucketId, objectId, ins, "image/jpeg");
    }

    /**
     * 数据流上传
     * 
     * @param bucketId
     * @param objectId
     * @param ins
     * @param contentType
     * @throws Exception
     */
    public static void uploadByStream(String bucketId, String objectId, InputStream ins, String contentType) throws Exception {
        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(ins.available());
        objectMeta.setContentType(contentType);

        client.putObject(bucketId, objectId, ins, objectMeta);
    }

    /**
     * byte数组上传(base64)
     * 
     * @param bucketId
     * @param objectId
     * @param content
     */
    public static void uploadByByteArray(String bucketId, String objectId, byte[] content) {
        uploadByByteArray(bucketId, objectId, content, "image/jpeg");
    }

    /**
     * byte数组上传(base64)
     * 
     * @param bucketId
     * @param objectId
     * @param content
     * @param contentType
     */
    public static void uploadByByteArray(String bucketId, String objectId, byte[] content, String contentType) {
        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(content.length);
        objectMeta.setContentType(contentType);
        client.putObject(bucketId, objectId, new ByteArrayInputStream(content), objectMeta);
    }

    /**
     * 断点续传
     * 
     * @param bucketId
     * @param objectId
     * @param localFile
     * @param contentType
     * @throws Throwable
     */
    public static void uploadByCheckpoint(String bucketId, String objectId, String localFile, String contentType) throws Throwable {
        UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketId, objectId);
        // 设置文件类型
        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentType(contentType);
        uploadFileRequest.setObjectMetadata(objectMeta);
        // 待上传的本地文件
        uploadFileRequest.setUploadFile(localFile);
        // 设置并发下载数，默认1
        uploadFileRequest.setTaskNum(5);
        // 设置分片大小，默认100KB
        uploadFileRequest.setPartSize(1024 * 1024 * 1);
        // 开启断点续传，默认关闭
        uploadFileRequest.setEnableCheckpoint(true);

        client.uploadFile(uploadFileRequest);
    }

    /**
     * 删除文件
     * 
     * @param bucketId
     * @param objectId
     * @throws OSSException
     * @throws ClientException
     */
    public static void deleteFile(String bucketId, String objectId) {
        client.deleteObject(bucketId, objectId);
    }

    /**
     * 获取公有访问链接
     * 
     * @param objectId
     * @return
     */
    public static String getPublicUrl(String objectId) {
        return publicDomain + objectId;
    }

    /**
     * 获取私有访问链接（默认1小时有效时间）
     * 
     * @param bucketId
     * @param objectId
     * @return
     */
    public static String getPrivateUrl(String bucketId, String objectId) {
        return getPrivateUrl(bucketId, objectId, 1000 * 3600, null);
    }

    /**
     * 获取私有访问链接
     * 
     * @param bucketId
     * @param objectId
     * @param expiration
     *            millisecond from now date
     * @return
     */
    public static String getPrivateUrl(String bucketId, String objectId, long expiration) {
        return getPrivateUrl(bucketId, objectId, expiration, null);
    }

    /**
     * 获取私有访问链接
     * 
     * @param bucketId
     * @param objectId
     * @param expiration
     *            millisecond from now date
     * @param style
     *            , like "style/your_own_rule"
     * @return
     */
    public static String getPrivateUrl(String bucketId, String objectId, long expiration, String style) {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        c.add(Calendar.HOUR_OF_DAY, 1);
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketId, objectId);
        req.setExpiration(c.getTime());
        req.setProcess(style);
        URL url = client.generatePresignedUrl(req);
        return url.toString();
    }

    /**
     * 
     * @param objectId
     * @param days
     *            days from now date
     * @return
     */
    public String getAccessURL(String bucketId, String objectId, int days) {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        c.add(Calendar.DAY_OF_MONTH, days);
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketId, objectId);
        req.setExpiration(c.getTime());
        URL url = client.generatePresignedUrl(req);
        return url.toString();
    }

    /**
     * 创建文件夹
     * 
     * @param bucketId
     * @param folderName
     * @throws IOException
     */
    public static void createFolder(String bucketId, String folderName) {
        client.putObject(bucketId, folderName, new ByteArrayInputStream(new byte[0]));
    }

    /**
     * 下载文件
     * 
     * @param bucketId
     * @param objectId
     * @param localFile
     */
    public static void downloadFile(String bucketId, String objectId, String localFile) {
        client.getObject(new GetObjectRequest(bucketId, objectId), new File(localFile));
    }

    /**
     * 断点下载
     * 
     * @param bucketId
     * @param objectId
     * @param localFile
     * @throws Throwable
     */
    public static void downloadByCheckpoint(String bucketId, String objectId, String localFile) throws Throwable {
        DownloadFileRequest downloadFileRequest = new DownloadFileRequest(bucketId, objectId);
        // 设置本地文件
        downloadFileRequest.setDownloadFile(localFile);
        // 设置并发下载数，默认1
        downloadFileRequest.setTaskNum(5);
        // 设置分片大小，默认100KB
        downloadFileRequest.setPartSize(1024 * 1024 * 1);
        // 开启断点续传，默认关闭
        downloadFileRequest.setEnableCheckpoint(true);

        client.downloadFile(downloadFileRequest);
    }

    /**
     * 获取上传文件的文件类型
     * 
     * @param bucketId
     * @param objectId
     * @return
     */
    public static String getContentType(String bucketId, String objectId) {
        ObjectMetadata metadata = client.getObjectMetadata(bucketId, objectId);
        return metadata.getContentType();
    }

}
