package rightwing.ut.dto.MainDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;

@Schema(name = "RequestDTO")
@Data
public class MainReqDto {
    @Schema(
            description = "A set of texts for translation",
            example = "[\n " +
                    "\"Hello\",\n" +
                    "\"world\" \n" +
                    "]"
    )
    ArrayList<String> texts;
    @Schema(
            description = "Required field. The language into which the text is being translated. Specified in ISO 639-1 format",
            example = "ru"
    )
    String targetLanguageCode;

    @Override
    public String toString() {
        return "!Req! " + texts.get(0);
    }
}
