package rightwing.ut.dto.auth;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
public class AuthYandexDto {
    String iamToken;
    String expiresAt;
}
