package com.qucai.kp.event;


public class SampleMain {

	public static void main(String[] args) {
		SpringContextHolder.getApplicationContext().publishEvent(
				new SimpleApplicationEvent(new Object()));
	}

}
