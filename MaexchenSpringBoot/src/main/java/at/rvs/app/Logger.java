package at.rvs.app;

import java.io.Serializable;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Logger implements Serializable {

	transient org.slf4j.Logger slf4jLogger;

	public Logger() {
	}

	public Logger(String name) {
		this.slf4jLogger = LoggerFactory.getLogger(name);
	}

	public void debug(String message) {
		slf4jLogger.debug(message);
	}

	public void debug(String message, Throwable e) {
		slf4jLogger.debug(message, e);
	}

	public void info(String message) {
		slf4jLogger.info(message);
	}

	public void info(String message, Throwable e) {
		slf4jLogger.info(message, e);
	}

	public void warn(String message) {
		slf4jLogger.warn(message);
	}

	public void warn(String message, Throwable e) {
		slf4jLogger.warn(message, e);
	}

	public void error(String message) {
		slf4jLogger.error(message);
	}

	public void error(String message, Throwable e) {
		slf4jLogger.error(message, e);
	}

}
