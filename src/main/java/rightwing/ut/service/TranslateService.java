package rightwing.ut.service;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import rightwing.ut.dto.MainDto.MainAnsDto;
import rightwing.ut.dto.MainDto.MainReqDto;
import rightwing.ut.dto.transYandex.AnsYandexDto;
import rightwing.ut.dto.transYandex.ReqYandexDto;
import rightwing.ut.exception.TranslatorsServiceForbiddenAuthException;
import rightwing.ut.exception.TranslatorsServiceUnavailableException;
import rightwing.ut.util.Utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Log4j2
@Service
public class TranslateService {
    private final AuthTokens authTokens;
    private final WebClient webClientYandexAuth;
    private final Utils utils;

    @Autowired
    public TranslateService(AuthTokens authTokens, @Qualifier("webClientAuthYandex") WebClient webClientYandexAuth, Utils utils) {
        this.authTokens = authTokens;
        this.webClientYandexAuth = webClientYandexAuth;
        this.utils = utils;
    }

    public MainAnsDto translateYandex(MainReqDto mainReqDto) {
        AnsYandexDto ansYandexDto = webClientYandexAuth
                .post()
                .body(BodyInserters.fromValue(new ReqYandexDto(mainReqDto, authTokens.getFolderId())))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, error -> {
                    log.info("WARNING! " + error.rawStatusCode() + " error");
                    return Mono.error(new TranslatorsServiceForbiddenAuthException("Yandex"));
                })
                .onStatus(HttpStatus::is5xxServerError, error -> {
                    log.info("WARNING! " + error.rawStatusCode() + " error");
                    return Mono.error(new TranslatorsServiceUnavailableException("Yandex"));
                })
                .bodyToMono(AnsYandexDto.class)
                .retryWhen(Retry.fixedDelay(2, Duration.ofMillis(100)))
                .doOnError(error -> Mono.error(new TranslatorsServiceUnavailableException("Yandex")))
                .onErrorResume(error -> Mono.just(new AnsYandexDto()))
                .block();
        return new MainAnsDto(Objects.requireNonNull(ansYandexDto), LocalDateTime.now());
    }
}
