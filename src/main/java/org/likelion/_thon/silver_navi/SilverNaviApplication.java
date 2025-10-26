package org.likelion._thon.silver_navi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SilverNaviApplication {

	public static void main(String[] args) {
		SpringApplication.run(SilverNaviApplication.class, args);
	}

}
