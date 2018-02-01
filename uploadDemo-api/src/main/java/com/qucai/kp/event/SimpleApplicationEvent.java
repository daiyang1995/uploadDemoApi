package com.qucai.kp.event;

import org.springframework.context.ApplicationEvent;

public class SimpleApplicationEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2927524038170955092L;

	public SimpleApplicationEvent(Object source) {
		super(source);
	}

}
