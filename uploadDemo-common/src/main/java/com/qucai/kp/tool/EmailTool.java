package com.qucai.kp.tool;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang3.StringUtils;

import com.sun.mail.util.MailSSLSocketFactory;

public class EmailTool {

	private static MimeMessage message;
	private static Session session;

	private static String senderUsername;
	private static String cc;
	// 默认邮件发送开启
	private static boolean onOff = true;

	private static boolean debug = true;

	static {
		InputStream in = EmailTool.class
				.getResourceAsStream("/mail.properties");
		try {
			Properties properties = new Properties();
			properties.load(in);

			boolean sslEnable = Boolean.valueOf(properties.getProperty("mail.smtp.ssl.enable"));
			if (sslEnable) {

				MailSSLSocketFactory sf = null;
				try {
					sf = new MailSSLSocketFactory();
					sf.setTrustAllHosts(true);
				} catch (GeneralSecurityException e1) {
					e1.printStackTrace();
				}

				// properties.put("mail.smtp.ssl.enable", "true");
				properties.put("mail.smtp.port", properties.getProperty("mail.smtp.ssl.port"));
				properties.put("mail.smtp.ssl.socketFactory", sf);
			} else {
				// this is the default set
				// properties.put("mail.smtp.ssl.enable", "false");
				properties.put("mail.smtp.port", properties.getProperty("mail.smtp.default.port"));
			}

			onOff = Boolean.valueOf(properties.getProperty("mail.onOff"));
			debug = Boolean.valueOf(properties.getProperty("mail.debug"));
			cc = properties.getProperty("mail.cc");

			final String username = properties.getProperty("mail.sender.username");
			final String password = properties.getProperty("mail.sender.password");
			senderUsername = username;

			session = Session.getDefaultInstance(properties,
					new Authenticator() {
						@Override
						protected PasswordAuthentication getPasswordAuthentication() {
							PasswordAuthentication pa = new PasswordAuthentication(
									username, password);
							return pa;
						}
					});

			message = new MimeMessage(session);

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
	 * 发送邮件
	 * 
	 * @param subject
	 *            邮件主题
	 * @param sendHtml
	 *            邮件内容
	 * @param receiveUser
	 *            收件人地址<br>
	 *            多个收件人用,分割
	 */
	public static void sendHtmlEmail(String subject, String sendHtml,
			String receiveUser) {
		sendHtmlEmail(subject, sendHtml, receiveUser, null);
	}

	/**
	 * 发送邮件
	 * 
	 * @param subject
	 *            邮件主题
	 * @param sendHtml
	 *            邮件内容
	 * @param receiveUser
	 *            收件人地址<br>
	 *            多个收件人用,分割
	 * @param attachmentList
	 *            附件
	 */
	public static void sendHtmlEmail(String subject, String sendHtml,
			String receiveUser, List<String> attachmentPathList) {
		// 如果关闭邮件发送开关，不发邮件
		if (!onOff) {
			return;
		}
		session.setDebug(debug);
		try {
			// 发件人
			InternetAddress from = new InternetAddress(senderUsername);
			message.setFrom(from);

			// 收件人
			InternetAddress[] toList = InternetAddress.parse(receiveUser);
			message.setRecipients(Message.RecipientType.TO, toList);

			// 抄送人
			if (StringUtils.isNotBlank(cc)) {
				InternetAddress[] ccList = InternetAddress.parse(cc);
				message.setRecipients(Message.RecipientType.CC, ccList);
			}

			// 邮件主题
			message.setSubject(subject);

			// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
			Multipart multipart = new MimeMultipart();

			// 添加邮件正文
			BodyPart contentPart = new MimeBodyPart();
			contentPart.setContent(sendHtml, "text/html;charset=UTF-8");
			multipart.addBodyPart(contentPart);

			// 添加附件的内容
			if (attachmentPathList != null && !attachmentPathList.isEmpty()) {
				for (String s : attachmentPathList) {
					File f = new File(s);
					BodyPart attachmentBodyPart = new MimeBodyPart();
					DataSource source = new FileDataSource(f);
					attachmentBodyPart.setDataHandler(new DataHandler(source));

					// MimeUtility.encodeWord可以避免文件名乱码
					attachmentBodyPart.setFileName(MimeUtility.encodeWord(f
							.getName()));
					multipart.addBodyPart(attachmentBodyPart);
				}
			}

			// 将multipart对象放到message中
			message.setContent(multipart);

			// 保存邮件
			message.saveChanges();

			Transport.send(message, message.getAllRecipients());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
