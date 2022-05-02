package rightwing.ut.dto.transYandex;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
public class AnsYandexDto {
    ArrayList<Translations> translations;

    @AllArgsConstructor
    public
    class Translations {
        String text;
        String detectedLanguageCode;
    }
}
