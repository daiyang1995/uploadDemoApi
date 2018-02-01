package com.qucai.kp.tool;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpProtocolException;

/**
 * Ftp Util
 * @author yhy 2017年6月2日
 *
 */
@SuppressWarnings({ "deprecation", "restriction" })
public class FtpTool {
    private static FtpClient ftpClient = null;
    private static String ftpHost;
    private static Integer ftpPort;
    private static String ftpUser;
    private static String ftpPassword;
    static {
        InputStream in = FtpTool.class.getResourceAsStream("/ftp.properties");
        try {
            Properties properties = new Properties();
            properties.load(in);

            ftpHost = properties.getProperty("ftp.host");
            ftpPort = Integer.parseInt(properties.getProperty("ftp.port"));
            ftpUser = properties.getProperty("ftp.user");
            ftpPassword = properties.getProperty("ftp.password");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取 ftp 链接
     * @author yhy 2017年6月2日
     */
    public static void connect() {
        try {
            ftpClient = FtpClient.create();
            ftpClient.setConnectTimeout(60 * 1000); // 一分钟，如果超过就判定超时了
            SocketAddress addr = new InetSocketAddress(ftpHost, ftpPort);
            ftpClient.connect(addr);
            ftpClient.login(ftpUser, ftpPassword.toCharArray());
            ftpClient.setBinaryType();
        } catch (FtpProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 断开 ftp 链接
     * @author yhy 2017年6月2日
     * @param ftpClient
     * @return
     */
    public static boolean closeConnect() {
        try {
            if (ftpClient != null) {
                ftpClient.close();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 变更 ftp 链接的当前所在目录
     * @author yhy 2017年6月2日
     * @param ftpClient
     * @param path
     * @throws Exception
     */
    public static void changeDirectory(String path) throws Exception {
        ftpClient.changeDirectory(path);
    }

    /**
     * 获取 ftp 当前链接所在目录的 文件及文件夹列表
     * @author yhy 2017年6月2日
     * @param ftpClient
     * @param path
     * @return
     */
    public static List<Object> getFileList(String path) {
        List<Object> list = new ArrayList<Object>();
        DataInputStream dis;
        try {
            dis = new DataInputStream(ftpClient.nameList(path));
            String filename = "";
            while ((filename = dis.readLine()) != null) {
                list.add(filename);
            }
        } catch (Exception e) {
            return null;
        }

        return list;
    }

    /**
     * 获取 ftp 当前链接所在目录的 likeName 文件及文件夹列表
     * @author yhy 2017年11月2日
     * @param ftpClient
     * @param path
     * @param likeName
     * @return
     */
    public static List<Object> getFileListByLike(String path, String likeName) {
        List<Object> list = new ArrayList<Object>();
        DataInputStream dis;
        try {
            dis = new DataInputStream(ftpClient.nameList(path));
            String filename = "";
            while ((filename = dis.readLine()) != null) {
                if (filename.indexOf(likeName) >= 0) {
                    list.add(filename);
                }
            }
        } catch (Exception e) {
            return null;
        }

        return list;
    }

    /**
     * 上传流文件，需要先 修改到 文件所在目录
     * @author yhy 2017年6月2日
     * @param ftpClient
     * @param ins 文件流
     * @param remoteName  远程ftp服务器别名
     * @param isOver 是否覆盖上传
     */
    public static void upload(InputStream ins, String remoteName, boolean isOver) {
        try {
            if (!"".equals(ftpClient.getStatus(remoteName)) && !isOver)//是否覆盖文件
                return;
            try (OutputStream os = ftpClient.putFileStream(remoteName); InputStream is = ins) {
                byte[] bytes = new byte[1024];// 创建一个缓冲区
                int c;
                while ((c = is.read(bytes)) > 0) {
                    os.write(bytes, 0, c);
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 上传 文件或文件夹 到 ftp 不做别名，需要先 修改到 文件所在目录
     * @author yhy 2017年6月2日
     * @param ftpClient
     * @param localFile
     * @param isOver
     */
    public static void upload(File localFile, boolean isOver) {
        if (localFile.isDirectory()) {
            try {
                try {
                    ftpClient.changeDirectory(localFile.getName());
                } catch (Exception ex) {
                    ftpClient.makeDirectory(localFile.getName());
                    ftpClient.changeDirectory(localFile.getName());
                }
                String[] files = localFile.list();
                File tempFile;
                for (int i = 0; i < files.length; i++) {
                    tempFile = new File(localFile.getPath() + "\\" + files[i]);
                    if (tempFile.isDirectory()) {
                        upload(tempFile, isOver);
                        ftpClient.changeToParentDirectory();
                    } else {
                        tempFile = new File(localFile.getPath() + "\\" + files[i]);
                        streamWrite(tempFile, isOver);
                    }
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else {
            streamWrite(localFile, isOver);
        }
    }

    /**
     * 写入文件，需要先 修改到 文件所在目录
     * @author yhy 2017年6月2日
     * @param ftpClient
     * @param localFile
     * @param isOver
     */
    private static void streamWrite(File localFile, boolean isOver) {
        try {
            if (!"".equals(ftpClient.getStatus(localFile.getName())) && !isOver)//是否覆盖文件
                return;
            try (OutputStream os = ftpClient.putFileStream(localFile.getName(), true);
                    InputStream is = new FileInputStream(localFile)) {
                byte[] bytes = new byte[1024];// 创建一个缓冲区
                int c;
                while ((c = is.read(bytes)) > 0) {
                    os.write(bytes, 0, c);
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 下载 ftp 文件流，需要先 修改到 文件所在目录
     * @author yhy 2017年6月2日
     * @param ftpClient
     * @param filename
     * @return
     */
    public static OutputStream downloadFile(String filename) {
        ByteArrayOutputStream swapStream = null;
        InputStream is = null;
        try {
            is = ftpClient.getFileStream(filename);
            swapStream = new ByteArrayOutputStream();
            int ch;
            while ((ch = is.read()) != -1) {
                swapStream.write(ch);
            }
        } catch (Exception e) {
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (swapStream != null) {
                    swapStream.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return swapStream;
    }

    /**
     * 下载 ftp 文件，需要先 修改到 文件所在目录
     * @author yhy 2017年6月2日
     * @param ftpClient
     * @param filename
     * @param newfilename
     * @return
     */
    public static long downloadFile(String filename, String newfilename) {
        long result = 0;
        InputStream is = null;
        FileOutputStream os = null;
        try {
            is = ftpClient.getFileStream(filename);
            File outfile = new File(newfilename);
            os = new FileOutputStream(outfile);
            byte[] bytes = new byte[is.available()];
            int c;
            while ((c = is.read(bytes)) != -1) {
                os.write(bytes, 0, c);
                result = result + c;
            }
        } catch (IOException | FtpProtocolException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
