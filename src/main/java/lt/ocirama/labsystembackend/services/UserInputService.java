package lt.ocirama.labsystembackend.services;

import lt.ocirama.labsystembackend.Validators.Validator;

import java.util.Scanner;

public final class UserInputService {

    public static String TextInput() {
        Scanner sc = new Scanner(System.in);
        String validationInput;
        do {
            validationInput = sc.nextLine();
            if (Validator.isValidText(validationInput)) {
                return validationInput;
            } else {
                System.out.println("Galima tik tekstinė įvestis");
            }
        } while (!Validator.isValidText(validationInput));
        return validationInput;
    }

    public static String NumberInput() {
        Scanner sc = new Scanner(System.in);
        String validationInput;
        do {
            validationInput = sc.nextLine();
            if (Validator.isValidNum(validationInput)) {
                return validationInput;
            } else {
                System.out.println("Galima tik skaičių įvestis");
            }
        } while (!Validator.isValidNum(validationInput));
        return validationInput;
    }
    public static String NumberOrEndInput() {
        Scanner sc = new Scanner(System.in);
        String validationInput;
        do {
            validationInput = sc.nextLine();
            if (Validator.isValidNum(validationInput)) {
                return validationInput;
            } else {
                System.out.println("Galima tik skaičių įvestis");
            }
        } while (!Validator.isValidNumOrEnd(validationInput));
        return validationInput;
    }

    public static String CommandInput() {
        Scanner sc = new Scanner(System.in);
        String validationInput;
        do {
            validationInput = sc.nextLine();
            if (Validator.isValidCommand(validationInput)) {
                return validationInput;
            } else {
                System.out.println("Tokios komandos nėra");
            }
        } while (!Validator.isValidCommand(validationInput));
        return validationInput;
    }

    public static String YesOrNoInput() {
        Scanner sc = new Scanner(System.in);
        String validationInput;
        do {
            validationInput = sc.nextLine();
            if (Validator.isValidYesOrNo(validationInput)) {
                return validationInput;
            } else {
                System.out.println("Tokios komandos nėra");
            }
        } while (!Validator.isValidYesOrNo(validationInput));
        return validationInput;
    }

    public static String BasicInput() {
        Scanner sc = new Scanner(System.in);
        String validationInput;
        validationInput = sc.nextLine();
        return validationInput;
    }

}
