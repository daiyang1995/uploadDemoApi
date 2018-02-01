package com.qucai.kp.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SampleListener implements SmartApplicationListener {

	@Override
	public boolean supportsEventType(
			final Class<? extends ApplicationEvent> eventType) {
		return eventType == SimpleApplicationEvent.class;
	}

	@Override
	public boolean supportsSourceType(final Class<?> sourceType) {
		// check if sourceType is the specified class
		// here is just an example
		return sourceType == Object.class;
	}

	@Async
	@Override
	public void onApplicationEvent(final ApplicationEvent event) {
		// do your business
	}

	@Override
	public int getOrder() {
		return 1;
	}

}