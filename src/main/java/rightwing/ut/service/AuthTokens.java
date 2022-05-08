package rightwing.ut.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AuthTokens {
    @Value("${authTokens.yandex.oathToken}")
    private String oathToken;
    @Value("${authTokens.yandex.folderId}")
    private String folderId;
    private String iamToken;

    public void setIamToken(String iamToken) {
        this.iamToken = iamToken;
    }
}
