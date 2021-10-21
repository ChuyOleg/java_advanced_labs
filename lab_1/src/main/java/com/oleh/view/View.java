package com.oleh.view;

public class View {

    public static final String CLOSE_PROGRAM_MESSAGE = "Thanks for interruption, Have a nice Day!";

    public static final String START_CHOICE = "Select an action   (1) start   (2) exit    => ";

    public static final String DIRECTORY_INVITATION = "Enter directory -> ";

    public static final String DIRECTORY_IS_NOT_EXIST = "Your answer isn`t a path of the directory.";

    public static final String INPUT_MIN_VALUE = "Enter min value -> ";

    public static final String INPUT_MAX_VALUE = "Enter max value -> ";

    public static final String MAX_VALUE_MUST_BE_BIGGER = "Max value must be bigger than min_value (min_value = %s)";

    public static final String ENTRY_IS_NOT_INTEGER = "Your entry isn`t integer. ";

    public void printMessage(String message) {
        System.out.print(message);
    }

    public void printMessageLn(String message) {
        System.out.println(message);
    }

}
