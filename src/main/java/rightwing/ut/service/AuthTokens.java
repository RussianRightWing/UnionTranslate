package rightwing.ut.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class AuthTokens {
    private final String oathToken = "${authTokens.yandex.oathToken}";
    private final String folderId = "${authTokens.yandex.folderId}";
    private String iamToken;
}
