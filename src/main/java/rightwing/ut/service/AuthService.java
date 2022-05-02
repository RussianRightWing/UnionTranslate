package rightwing.ut.service;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        AuthYandexDto authYandexDto =  webClientAuthYandex
                .post()
                .body(BodyInserters.fromValue(authTokens))
                .retrieve()
                .bodyToMono(AuthYandexDto.class)
                .retryWhen(Retry.fixedDelay(3, Duration.ofMillis(100)))
                .doOnError(error -> log.info("Failed task: new auth token"))
                .block();
        if (authYandexDto != null) {
            authTokens.setIamToken(authYandexDto.getIamToken());
            log.info("Successful task: new auth token " + authTokens.getIamToken());
        }
    }
}
