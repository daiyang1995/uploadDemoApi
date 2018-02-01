package com.qucai.kp.context;


public enum AliOssBucketPrefix {
	
	/**
	 * 理赔附件<br>
	 * %s：案件编号（如：ECA1508318038631）<br>
	 * %s：objectId
	 */
	CLAIM_ATTACH("claim/%s/attach/%s");

	private String key;

	private AliOssBucketPrefix(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return key;
	}
	
	public static String genFullObject(AliOssBucketPrefix aobf, Object... args) {
		return String.format(aobf.toString(), args);
	}

}
