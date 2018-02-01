package com.qucai.kp.tool;

public class SerialNoTool {
    
    /**
     * 计划（简单版方案）编号
     * @return
     */
    public static synchronized String planEasyModeNo() {
        return "PEM" + System.currentTimeMillis();
    }
    
    /**
     * 方案（专业版方案）编号
     * @return
     */
    public static synchronized String planProModeNo() {
        return "PPM" + System.currentTimeMillis();
    }
    
    /**
     * 批次号
     * @return
     */
    public static synchronized String batchNo() {
        return "BCH" + System.currentTimeMillis();
    }
    
    /**
     * 批次流水号
     * @return
     */
    public static synchronized String batchFlowNo() {
        return "BCH-FL" + System.currentTimeMillis();
    }
    
    /**
     * 本地系统生成的保单号，非保险公司保单号
     * @return
     */
    public static synchronized String policyNoLocal() {
        return "TMP-" + System.currentTimeMillis();
    }
    
    /**
     * 理赔序列号
     * @return
     */
	public static synchronized String claimFlowNo() {
		return "CA" + System.currentTimeMillis();
	}
	
	/**
     * 报案序列号
     * @return
     */
	public static synchronized String entClaimFlowNo() {
		return "ECA" + System.currentTimeMillis();
	}
	
	/**
     * 财务订单号
     * @return
     */
	public static synchronized String accountTaskNo() {
		return "ACT" + System.currentTimeMillis();
	}
	
	/**
     * 财务详情订单号
     * @return
     */
	public static synchronized String accountDetailTaskNo() {
		return "ACT-DT" + System.currentTimeMillis();
	}
}
