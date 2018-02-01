package com.qucai.kp.common;

import com.qucai.kp.enums.RetEnumIntf;


/**
 * 业务返回码定义规则如下：<br>
 * 模块名_方法名_返回码<br>
 * 每个方法至少保留一项默认返回码<br>
 * ret：如010101：01(模块)01(方法)01(异常)，其中，模块编号00代表业务系统的自定义系统级错误<br>
 * 
 * @author owner
 *
 */
public enum ExRetEnum implements RetEnumIntf {

	// 基本
	SUCCESS("0", "成功"), 
	FAIL("-1", "失败");


	private String ret;
	private String msg;

	ExRetEnum(String ret, String msg) {
		this.ret = ret;
		this.msg = msg;
	}

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
