package behaviourtests;

public class CprGenerator {
    public static String generate(){
        String sixDigits =  String.valueOf(Math.random() * 999999);
        String fourDigits = String.valueOf(Math.random() * 9999);
        return sixDigits + "-" + fourDigits;
    }
}
