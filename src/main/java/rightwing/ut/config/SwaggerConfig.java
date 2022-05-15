package rightwing.ut.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Union translate API")
                                .version("0.1")
                                .contact(
                                        new Contact()
                                                .url("https://github.com/RussianRightWing")
                                                .name("Iskorkin Aleksei")
                                )
                );
    }
}
