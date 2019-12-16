package lt.ocirama.labsystembackend.Validators;

import java.util.regex.Pattern;

public final class Validator {

    public static boolean isValidCommand(String command) {
        if (!(command.equals("push") || command.equals("Baigta"))) {
            return false;
        } else return true;
    }

    public static boolean isValidText(String strTxt) {
        if (Pattern.compile("[0-9]").matcher(strTxt).find()) {
            return false;
        } else return true;
    }

    public static boolean isValidNum(String strNum) {
        if (!(Pattern.compile("[0-9]").matcher(strNum).find())) {
            return false;
        } else return true;
    }

    public static boolean isValidYesOrNo(String command) {
        if (!(command.equals("Taip") || command.equals("Ne"))) {
            return false;
        } else return true;
    }

    public static boolean isValidNumEndOrNext(String strNum) {
        if ((Pattern.compile("[0-9]").matcher(strNum).find()) || strNum.equals("Baigta")|| strNum.equals("Kitas")) {
            return true;
        } else return false;
    }
}
