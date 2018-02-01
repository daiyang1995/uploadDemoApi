package com.qucai.kp.kd100.subscribe;

import java.util.HashMap;

public class SubscribeReq {
	/**
	 * 订阅的快递公司的编码，一律用小写字母
	 */
	private String company;
	/**
	 * 订阅的快递单号，单号的最大长度是32个字符
	 */
	private String number;
	/**
	 * 出发地城市，省-市-区，非必填，填了有助于提升签收状态的判断的准确率，请尽量提供
	 */
	private String from;
	/**
	 * 目的地城市，省-市-区，非必填，填了有助于提升签收状态的判断的准确率，且到达目的地后会加大监控频率，请尽量提供
	 */
	private String to;
	/**
	 * 授权码，签合同后发放
	 */
	private String key;
//	private String src;
	/**
	 * 可包含的参数如：<br>
	 * callbackurl：您的回调接口的地址，如http://www.您的域名.com/kuaidi?callbackid=...<br>
	 * salt：签名用随机字符串（可选）<br>
	 * resultv2：添加此字段表示开通行政区域解析功能（仅对开通签收状态服务用户有效）<br>
	 * mobiletelephone：收件人的手机号，提供后快递100会向该手机号对应的QQ号推送【发货】、【同城派件】、【签收】三种信息，非必填，只能填写一个<br>
	 * seller：寄件商家的名称。平台方名称或商家名称。提供后快递100会向mobiletelephone发送的信息中署上seller的名称，非必填<br>
	 * commodity：寄给收件人的商品名。提供后快递100会向mobiletelephone发送的信息中署上commodity的名称，非必填
	 */
	private HashMap<String, String> parameters = new HashMap<String, String>();

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public HashMap<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(HashMap<String, String> parameters) {
		this.parameters = parameters;
	}

}
