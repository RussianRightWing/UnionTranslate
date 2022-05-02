package rightwing.ut.exception;

public class TranslatorsServiceUnavailableException extends CustomException {
    /**
     * Ошибка недоступности сервисов перевода
     * @param message какой сервис недоступен
     */
    public TranslatorsServiceUnavailableException(String message) {
        super(message + " services are not responding");
    }
}
