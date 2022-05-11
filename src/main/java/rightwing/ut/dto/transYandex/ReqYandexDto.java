package rightwing.ut.dto.transYandex;

import lombok.Data;
import rightwing.ut.dto.MainDto.MainReqDto;

import java.util.ArrayList;

@Data
public class ReqYandexDto {
    String folderId;
    ArrayList<String> texts;
    String targetLanguageCode;

    public ReqYandexDto(MainReqDto mainReqDto, String folderId) {
        this.texts = mainReqDto.getTexts();
        this.targetLanguageCode = mainReqDto.getTargetLanguageCode();
        this.folderId = folderId;
    }
}
