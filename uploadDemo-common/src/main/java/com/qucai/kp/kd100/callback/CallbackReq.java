package com.qucai.kp.kd100.callback;


/**
 * 示例：
 * {"status":"check","billstatus":"polling","message":"到达","lastResult":{"message":"ok","status":"200"}}
 * @author owner
 *
 */
public class CallbackReq {

	private String status;
	private String billstatus;
	private String message;
	private Kd100LastResult lastResult = new Kd100LastResult();

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBillstatus() {
		return billstatus;
	}

	public void setBillstatus(String billstatus) {
		this.billstatus = billstatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Kd100LastResult getLastResult() {
		return lastResult;
	}

	public void setLastResult(Kd100LastResult lastResult) {
		this.lastResult = lastResult;
	}

}
