package rightwing.ut.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rightwing.ut.dto.MainDto.MainAnsDto;
import rightwing.ut.dto.MainDto.MainReqDto;
import rightwing.ut.service.TranslateService;

@Tag(name = "Yandex Translate controller", description = "Rest controller for text translation using Yandex Translation")
@RestController
@Log4j2
@RequestMapping("/v1")
public class TranslateController {
    TranslateService translateService;

    @Autowired
    public TranslateController(TranslateService translateService) {
        this.translateService = translateService;
    }

    @Operation(summary = "Translate", description = "Translates the text into the specified language.")
    @PostMapping("/translate")
    public ResponseEntity<MainAnsDto> translate(@RequestBody MainReqDto reqDto) {
        return ResponseEntity.ok(translateService.translateYandex(reqDto));
    }

//    @PostMapping("/detectLanguage")
//    public ResponseEntity<>
}
