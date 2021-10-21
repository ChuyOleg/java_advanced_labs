package com.oleh.utils;

import com.oleh.view.View;

import java.util.Scanner;

public class InputUtility {

    private static final Scanner sc = new Scanner(System.in);

    public static String inputStringValueWithScanner(View view, String invitation_message) {
        view.printMessage(invitation_message);
        return sc.nextLine();
    }

    public static int inputIntValueWithScanner(View view, String invitation_message, String message_for_wrong_type) {
        view.printMessage(invitation_message);
        while(!sc.hasNextInt()) {
            view.printMessage(message_for_wrong_type + invitation_message);
            sc.nextLine();
        }
        return Integer.parseInt(sc.nextLine());
    }

}
