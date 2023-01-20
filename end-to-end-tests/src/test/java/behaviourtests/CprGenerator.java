//@Author: Emily s223122

package behaviourtests;

import java.time.LocalDate;
import java.util.Random;

public class CprGenerator {
    public static String generate() {
        Random random = new Random();
        LocalDate birthday = LocalDate.ofEpochDay(random.nextInt((int) LocalDate.now().toEpochDay()));
        String sixDigits = String.format("%02d%02d%02d", birthday.getDayOfMonth(), birthday.getMonthValue(), birthday.getYear() % 100);
        String fourDigits = String.format("%04d", random.nextInt(9999) + 1);
        return sixDigits + "-" + fourDigits;
    }
}