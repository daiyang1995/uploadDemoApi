package com.qucai.kp.context;

public enum RedisPrefix {

	REDIS_KEY_PREFIX("tpa-bwf-"), 
	REDIS_KEY_CONFIG_PREFIX(REDIS_KEY_PREFIX + "cfg-"), 
	REDIS_KEY_DICT_GROUP_PREFIX(REDIS_KEY_PREFIX + "dg-");
	
	private String key;

	private RedisPrefix(String key) {
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

}
