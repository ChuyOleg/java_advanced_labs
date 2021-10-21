package com.oleh.controller;

import com.oleh.tasks.HandleDirectoryTask;
import com.oleh.utils.InputUtility;
import com.oleh.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Controller {

    private final ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private final View view = new View();

    public void runProgram() {

        while (true) {
            String selection = InputUtility.inputStringValueWithScanner(view, View.START_CHOICE);
            processUserSelection(selection);
        }

    }

    private void processUserSelection(String selection) {
        switch (selection) {
            case "start": case "1":
                startSearch();
                break;
            case "exit": case "2":
                closeProgram();
                break;
        }
    }

    private void startSearch() {

//        BlockingQueue<Future<?>> futures = new ArrayBlockingQueue<>(1000);
        List<HandleDirectoryTask> tasks = new ArrayList<>();

        File dir = getFilePathFromUser();

        int min_value = getMinValueFromUser();
        int max_value = getMaxValueFromUser(min_value);

//        HandleDirectoryTask running = new HandleDirectoryTask(dir, service, futures, min_value, max_value);

    }

    private void closeProgram() {
        service.shutdownNow();
        view.printMessageLn(View.CLOSE_PROGRAM_MESSAGE);
        System.exit(0);
    }

    private File getFilePathFromUser() {
        while (true) {
            File dir = new File(InputUtility.inputStringValueWithScanner(view, View.DIRECTORY_INVITATION));

            if (dir.isDirectory()) {
                return dir;
            } else {
                view.printMessageLn(View.DIRECTORY_IS_NOT_EXIST);
            }
        }
    }

    private int getMinValueFromUser() {
        return InputUtility.inputIntValueWithScanner(view, View.INPUT_MIN_VALUE, View.ENTRY_IS_NOT_INTEGER);
    }

    private int getMaxValueFromUser(int min_value) {
        while (true) {
            int max_value = InputUtility.inputIntValueWithScanner(view, View.INPUT_MAX_VALUE, View.ENTRY_IS_NOT_INTEGER);
            if (max_value > min_value) return max_value;
            else view.printMessageLn(String.format(View.MAX_VALUE_MUST_BE_BIGGER, min_value));
        }
    }

}
