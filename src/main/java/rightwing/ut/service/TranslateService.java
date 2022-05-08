package rightwing.ut.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import rightwing.ut.dto.MainDto.MainAnsDto;
import rightwing.ut.dto.MainDto.MainReqDto;
import rightwing.ut.dto.transYandex.AnsYandexDto;
import rightwing.ut.dto.transYandex.ReqYandexDto;
import rightwing.ut.exception.TranslatorsServiceUnavailableException;
import rightwing.ut.util.Utils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

@Log4j2
@Service
public class TranslateService {
    private final AuthTokens authTokens;
    private final Utils utils;

    @Resource(name = "webClientTranslateYandex")
    WebClient webClientTranslateYandex;

    @Autowired
    public TranslateService(AuthTokens authTokens, Utils utils) {
        this.authTokens = authTokens;
        this.utils = utils;
    }

    public MainAnsDto translateYandex(MainReqDto mainReqDto) {
        AnsYandexDto ansYandexDto = webClientTranslateYandex
                .post()
                .body(BodyInserters.fromValue(new ReqYandexDto(mainReqDto, authTokens.getFolderId())))
                .exchangeToMono(response -> {
                    if(response.statusCode() == HttpStatus.OK){
                        return response.bodyToMono(AnsYandexDto.class);
                    } else {
                        throw new TranslatorsServiceUnavailableException(response.rawStatusCode() + "Yandex");
                    }
                })
                .block();
        return new MainAnsDto(Objects.requireNonNull(ansYandexDto), LocalDateTime.now());
    }
}
