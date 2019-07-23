package at.rvs.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "at.rvs.*")
@EnableScheduling
public class MaexchenSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(MaexchenSpringBootApplication.class, args);
	}

}
