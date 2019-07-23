package at.rvs.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "at.rvs.*")
public class MaexchenSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(MaexchenSpringBootApplication.class, args);
	}

}
