package rightwing.ut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UnionTranslateApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnionTranslateApplication.class, args);
	}

}
