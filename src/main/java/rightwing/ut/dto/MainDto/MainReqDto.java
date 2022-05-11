package rightwing.ut.dto.MainDto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class MainReqDto {
    ArrayList<String> texts;
    String targetLanguageCode;

    @Override
    public String toString() {
        return "!Req! " + texts.get(0);
    }
}
