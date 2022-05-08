package rightwing.ut.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import rightwing.ut.exception.TranslatorsServiceForbiddenAuthException;
import rightwing.ut.exception.TranslatorsServiceUnavailableException;

import java.util.HashMap;

@Log4j2
@ControllerAdvice
public class ExceptionController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @ExceptionHandler({TranslatorsServiceForbiddenAuthException.class, TranslatorsServiceUnavailableException.class})
    public ResponseEntity<String> translatorsServiceExceptionHandler(RuntimeException e) throws JsonProcessingException {
        log.info(e.getMessage() + " service exception");
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(
                objectMapper.writeValueAsString(new HashMap<String, String>() {{
                    put("service", "YandexTranslate");
                    put("code", e.getMessage().substring(0, 3));
                    put("message", e.getMessage().substring(3));
                }}),
                httpHeaders,
                HttpStatus.SERVICE_UNAVAILABLE);
    }
}