package magisterka.SoapBackend.HelperClasses;

public class PairOfTexts {
    String text1;
    String text2;
    PairOfTexts(String text1, String text2){
        this.text1 = text1;
        this.text2 = text2;
    }

    public String getText1() {
        return text1;
    }

    public String getText2() {
        return text2;
    }
}
