package lt.ocirama.labsystembackend.Validators;

public final class InputCheck {

    public static String isText (String input){
        do {
            return input;
        } while( !Validator.isValid(input));

    }

    public static Integer isNumber (Integer input){
        do {
            return input;
        } while( !Validator.isValid(input));

    }

    public static String isCommand (String input){
        do {
            return input;
        } while( !Validator.isValid(input));

    }
}
