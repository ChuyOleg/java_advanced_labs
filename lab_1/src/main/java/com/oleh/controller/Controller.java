package com.oleh.controller;

import com.oleh.tasks.FindAllDirectoriesTask;
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

    @SuppressWarnings("InfiniteLoopStatement")
    public void runProgram() {
        while (true) {
            String user_selection = InputUtility.inputStringValueWithScanner(view, View.START_CHOICE);
            processUserSelection(user_selection);
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

        BlockingQueue<File> dirs = new ArrayBlockingQueue<>(10);

        File main_dir = getFilePathFromUser();

        int min_value = getMinValueFromUser();
        int max_value = getMaxValueFromUser(min_value);

        try {
            List<Future<Object>> futures = runThreads(dirs, main_dir, min_value, max_value);

            waitThreadsEnd(futures);
        } catch (InterruptedException | ExecutionException e) {
            view.printMessageLn(View.THREAD_EXECUTION_EXCEPTION);
        }

    }

    private List<Future<Object>> runThreads(BlockingQueue<File> dirs, File main_dir, int min_value, int max_value) throws InterruptedException {
        List<Future<Object>> futures = new ArrayList<>();

        Future<Object> find_dirs_future = service.submit(new FindAllDirectoriesTask(view, dirs, main_dir));
        futures.add(find_dirs_future);

        while (true) {
            File dir = dirs.take();
            if (dir.getName().equals("")) {
                break;
            }
            Future<Object> handle_future = service.submit(new HandleDirectoryTask(view, dir, min_value, max_value));
            futures.add(handle_future);
        }

        return futures;
    }

    private void waitThreadsEnd(List<Future<Object>> futures) throws ExecutionException, InterruptedException {
        for (Future<Object> future : futures) {
            future.get();
        }
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

    private void closeProgram() {
        service.shutdownNow();
        view.printMessageLn(View.CLOSE_PROGRAM_MESSAGE);
        System.exit(0);
    }

}
