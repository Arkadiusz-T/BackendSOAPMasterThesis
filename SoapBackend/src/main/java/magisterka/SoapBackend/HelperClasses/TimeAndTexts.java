package magisterka.SoapBackend.HelperClasses;

import java.util.List;

public class TimeAndTexts {
    Long time;
    List<String> texts;

    public Long getTime() {
        return time;
    }

    public List<String> getTexts() {
        return texts;
    }

    public TimeAndTexts(Long time, List<String> texts) {
        this.time = time;
        this.texts = texts;
    }
}
