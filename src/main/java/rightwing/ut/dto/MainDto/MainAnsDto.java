package rightwing.ut.dto.MainDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import rightwing.ut.dto.transYandex.AnsYandexDto;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
public class MainAnsDto {
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    LocalDateTime date;
    ArrayList<AnsYandexDto.Translations> translationYandex;

    public MainAnsDto(AnsYandexDto ansYandexDto, LocalDateTime date) {
        this.date = date;
        this.translationYandex = ansYandexDto.getTranslations();
    }

}
