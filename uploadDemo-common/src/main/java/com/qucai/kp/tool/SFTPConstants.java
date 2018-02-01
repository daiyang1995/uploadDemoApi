package com.qucai.kp.tool;

/**
 * SFTP 成员变量
 * @class SFTPConstants.java
 * 
 * @author XL.Pei
 * 
 * @date 2017年2月21日-下午2:38:50
 *
 */
public class SFTPConstants {
	/**
	 * Host:主机IP
	 */
	public static final String SFTP_REQ_HOST = "host";
	/**
	 * Port:主机ssh2登陆端口
	 */
	public static final String SFTP_REQ_PORT = "port";
	/**
	 * userName:主机登陆用户名
	 */
	public static final String SFTP_REQ_USERNAME = "username";
	/**
	 * pwd:主机登陆密码
	 */
	public static final String SFTP_REQ_PASSWORD = "password";
	/**
	 * 默认Port
	 */
	public static final int SFTP_DEFAULT_PORT = 22;
	/**
	 * 密钥的密码
	 */
	public static final String SFTP_REQ_PASSPHRASE = "passphrase";
	/**
	 * 密钥文件路径
	 */
	public static final String SFTP_REQ_PRIVATEKEY = "privateKey";

}
