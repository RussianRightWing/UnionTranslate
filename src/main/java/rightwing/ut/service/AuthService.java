package rightwing.ut.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;
import rightwing.ut.dto.auth.AuthYandexDto;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@EnableAsync
@Service
public class AuthService {
    private final AuthTokens authTokens;
    private final WebClient webClientAuthYandex;

    @Autowired
    public AuthService(AuthTokens authTokens, @Qualifier("webClientAuthYandex") WebClient webClientAuthYandex) {
        this.authTokens = authTokens;
        this.webClientAuthYandex = webClientAuthYandex;
    }

    @Async
    @Scheduled(fixedRateString = "${scheduler.yandex.intervalMs}")
    public void iamToken() {
        log.info("Start task: new auth token");
        Map<String, String> body = new HashMap<>();
        body.put("yandexPassportOauthToken", authTokens.getOathToken());
        AuthYandexDto authYandexDto = null;
        try {
            authYandexDto = webClientAuthYandex
                    .post()
                    .body(BodyInserters.fromValue(body))
                    .retrieve()
                    .bodyToMono(AuthYandexDto.class)
                    .retryWhen(Retry.fixedDelay(3, Duration.ofMillis(10000)))
                    .doOnError(error -> {
                        log.info("Failed task: new auth token");
                    })
                    .block();
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
        if (authYandexDto != null) {
            authTokens.setIamToken(authYandexDto.getIamToken());
            log.info("Successful task: new auth token " + authTokens.getIamToken());
        }
    }
}
