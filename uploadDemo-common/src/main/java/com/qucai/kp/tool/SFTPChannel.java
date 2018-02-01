package com.qucai.kp.tool;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import com.qucai.kp.context.SysInfo;

/**
 * SFTP 工具类
 * 
 * @class SFTPChannel.java
 * 
 * @author XL.Pei
 * 
 * @date 2017年2月21日-下午2:44:49
 *
 */
public class SFTPChannel {
	private static final int timeout = 60000;
	private Session session = null;
	private Channel channel = null;
	/**
	 * 初始化SFTP连接
	 * 
	 * @param channel
	 * @return
	 * @throws JSchException
	 */
	public ChannelSftp initSFTPChannel(SFTPChannel channel) throws JSchException {
		Map<String, String> sftpConfigs = new HashMap<String, String>();
		sftpConfigs.put(SFTPConstants.SFTP_REQ_HOST, SysInfo.CONFIG.get("SFTP_HOST"));
		sftpConfigs.put(SFTPConstants.SFTP_REQ_PORT, SysInfo.CONFIG.get("SFTP_PORT"));
		sftpConfigs.put(SFTPConstants.SFTP_REQ_USERNAME, SysInfo.CONFIG.get("SFTP_USER_QC"));
		sftpConfigs.put(SFTPConstants.SFTP_REQ_PASSWORD, SysInfo.CONFIG.get("SFTP_PASSWORD_QC"));
		return channel.getChannel(sftpConfigs, timeout);
	}

	/**
	 * SFTP 建立会话连接
	 * 
	 * @param SFTPConfigs
	 *            配置信息
	 * @param timeout
	 *            连接超时时间
	 * @return ChannelSftp 返回对象
	 * @throws JSchException
	 */
	public ChannelSftp getChannel(Map<String, String> SFTPConfigs, int timeout) throws JSchException {

		String ftpHost = SFTPConfigs.get(SFTPConstants.SFTP_REQ_HOST);
		String port = SFTPConfigs.get(SFTPConstants.SFTP_REQ_PORT);
		String ftpUserName = SFTPConfigs.get(SFTPConstants.SFTP_REQ_USERNAME);
		String ftpPassword = SFTPConfigs.get(SFTPConstants.SFTP_REQ_PASSWORD);
		String passphrase = SFTPConfigs.get(SFTPConstants.SFTP_REQ_PASSPHRASE);
		String privateKey = SFTPConfigs.get(SFTPConstants.SFTP_REQ_PRIVATEKEY);

		int ftpPort = SFTPConstants.SFTP_DEFAULT_PORT;
		if (port != null && !port.equals("")) {
			ftpPort = Integer.valueOf(port);
		}
		// 创建JSch对象
		JSch jsch = new JSch();
		// 设置密钥和密码
		if (privateKey != null && !"".equals(privateKey)) {
			if (passphrase != null && "".equals(passphrase)) {
				// 设置带口令的密钥
				jsch.addIdentity(privateKey, passphrase);
			} else {
				// 设置不带口令的密钥
				jsch.addIdentity(privateKey);
			}
		}
		session = jsch.getSession(ftpUserName, ftpHost, ftpPort); // 根据用户名，主机ip，端口获取一个Session对象
		if (ftpPassword != null) {
			session.setPassword(ftpPassword); // 设置密码
		}
		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config); // 为Session对象设置properties
		session.setTimeout(timeout); // 设置timeout时间
		session.connect(); // 通过Session建立链接
		System.out.println("链接到SFTP成功>>-------<" + ftpHost + ">");

		channel = session.openChannel("sftp"); // 打开SFTP通道
		channel.connect(); // 建立SFTP通道的连接
		return (ChannelSftp) channel;
	}

	/**
	 * 关闭SFTP
	 * 
	 * @throws Exception
	 */
	public void closeChannel() throws Exception {
		if (channel != null) {
			channel.disconnect();
		}
		if (session != null) {
			session.disconnect();
		}
	}

	/**
	 * 判断目录是否存在
	 * 
	 * @param directory
	 * @param sftp
	 * @return
	 * @throws SftpException
	 */
	public boolean isDirExist(String directory, ChannelSftp sftp) throws SftpException {
		boolean isDirExistFlag = false;
		try {
			SftpATTRS sftpATTRS = sftp.lstat(directory);
			isDirExistFlag = true;
			return sftpATTRS.isDir();
		} catch (Exception e) {
			if (e.getMessage().toLowerCase().equals("no such file")) {
				isDirExistFlag = false;
			}
		}
		return isDirExistFlag;
	}
}
