package rightwing.ut.dto.auth;

import lombok.Data;

@Data
public class AuthYandexDto {
    String iamToken;
    String expiresAt;
}
