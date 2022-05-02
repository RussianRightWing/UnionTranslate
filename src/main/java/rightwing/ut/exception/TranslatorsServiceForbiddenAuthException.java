package rightwing.ut.exception;

public class TranslatorsServiceForbiddenAuthException extends CustomException{
    /**
     * Ошибка овторизации запроса на сервис перевода
     * @param message название сервиса
     */
    public TranslatorsServiceForbiddenAuthException(String message) {
        super(message + " forbidden authentication");
    }
}
