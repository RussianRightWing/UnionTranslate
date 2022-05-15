package rightwing.ut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableKafka
@PropertySource({
		"classpath:kafka.properties"
})
public class UnionTranslateApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnionTranslateApplication.class, args);
	}

}
