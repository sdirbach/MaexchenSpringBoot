package at.rvs.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfiguration implements ApplicationListener<ApplicationReadyEvent> {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationConfiguration.class);

	public void onApplicationEvent(ApplicationReadyEvent event) {
		logger.info("Application Starting up");
	}

}
