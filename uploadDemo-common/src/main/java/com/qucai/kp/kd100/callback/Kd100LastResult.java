package com.qucai.kp.kd100.callback;

import java.util.ArrayList;

public class Kd100LastResult {

	/**
	 * 消息体，请忽略
	 */
	private String message = "";
	/**
	 * 快递单当前签收状态<br>
	 * 包括0：在途中、1：已揽收、2：疑难、3：已签收、4：退签、5：同城派送中、6：退回、7：转单等7个状态<br>
	 * 其中4-7需要另外开通才有效
	 */
	private String state = "0";
	/**
	 * 通讯状态，请忽略
	 */
	private String status = "0";
	/**
	 * 快递单明细状态标记，暂未实现，请忽略
	 */
	private String condition = "";
	/**
	 * 是否签收标记，明细状态请参考state字段
	 */
	private String ischeck = "0";
	/**
	 * 快递公司编码,一律用小写字母
	 */
	private String com = "";
	/**
	 * 单号
	 */
	private String nu = "";
	/**
	 * 全量跟踪信息<br>
	 * 即从货物的第一条跟踪状态到最新的跟踪状态
	 */
	private ArrayList<Kd100record> data = new ArrayList<Kd100record>();

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNu() {
		return nu;
	}

	public void setNu(String nu) {
		this.nu = nu;
	}

	public String getCom() {
		return com;
	}

	public void setCom(String com) {
		this.com = com;
	}

	public ArrayList<Kd100record> getData() {
		return data;
	}

	public void setData(ArrayList<Kd100record> data) {
		this.data = data;
	}

	public String getIscheck() {
		return ischeck;
	}

	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

}
