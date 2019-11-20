package lt.ocirama.labsystembackend.Validators;

public final class Validator {
   /*do {
        // Displaying a message on the screen
        System.out.println("What room are you in? ");
        room = scanner.nextInt();
    } while( !isValid(room) );*/

    public static boolean isValidCommand(String command) {
        if (!command.equals("push") || !command.equals("Baigti")) {
            System.out.println("Tokios komandos nėra");
            return false;
        } else return true;
    }

    public static boolean isValid(String text) {
        if (!(text instanceof String)) {
            System.out.println("Galima tik tekstinė įvestis");
            return false;
        } else return true;
    }

    public static boolean isValid(Integer number) {
        if (!(number instanceof Integer)) {
            System.out.println("Galima tik skaičių įvestis");
            return false;
        } else return true;
    }


}
