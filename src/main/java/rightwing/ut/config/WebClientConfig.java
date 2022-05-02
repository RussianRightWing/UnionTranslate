package rightwing.ut.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import rightwing.ut.service.AuthTokens;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {
    private static final String YANDEX_AUTH_URL = "https://iam.api.cloud.yandex.net/iam/v1/tokens";
    private static final String YANDEX_TRANSLATE_URL = "https://translate.api.cloud.yandex.net/translate/v2/translate";
    private static final int TIMEOUT_YANDEX_AUTH_REQ = 5000;
    private final AuthTokens authTokens;

    @Autowired
    public WebClientConfig(AuthTokens authTokens) {
        this.authTokens = authTokens;
    }

    @Bean
    public WebClient webClientAuthYandex() {
        HttpClient httpClient = HttpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT_YANDEX_AUTH_REQ)
                .responseTimeout(Duration.ofMillis(TIMEOUT_YANDEX_AUTH_REQ))
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(TIMEOUT_YANDEX_AUTH_REQ, TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(TIMEOUT_YANDEX_AUTH_REQ, TimeUnit.MILLISECONDS));
                });
        return WebClient.builder()
                .baseUrl(YANDEX_AUTH_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeaders(h -> h.setBearerAuth(authTokens.getOathToken()))
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    @Bean
    public WebClient webClientTranslateYandex() {
        return WebClient.builder()
                .baseUrl(YANDEX_TRANSLATE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeaders(h -> h.setBearerAuth(authTokens.getIamToken()))
                .build();
    }
}
