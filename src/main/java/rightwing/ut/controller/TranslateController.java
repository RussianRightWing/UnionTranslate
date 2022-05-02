package rightwing.ut.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rightwing.ut.dto.MainDto.MainAnsDto;
import rightwing.ut.dto.MainDto.MainReqDto;
import rightwing.ut.service.TranslateService;

@RestController
@Log4j2
@RequestMapping("/v1")
public class TranslateController {
    TranslateService translateService;

    @Autowired
    public TranslateController(TranslateService translateService) {
        this.translateService = translateService;
    }

    @PostMapping("/translate")
    public ResponseEntity<MainAnsDto> translate(@RequestBody MainReqDto reqDto) {
        log.info(reqDto.toString());
        return ResponseEntity.ok(translateService.translateYandex(reqDto));
    }

//    @PostMapping("/detectLanguage")
//    public ResponseEntity<>
}
