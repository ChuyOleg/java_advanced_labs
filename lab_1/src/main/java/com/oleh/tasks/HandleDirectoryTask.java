package com.oleh.tasks;

import com.google.common.io.Files;
import com.oleh.utils.ReadFileUtility;
import com.oleh.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.Callable;

public class HandleDirectoryTask implements Callable<Object> {

    private final View view;
    private final File startDir;
    private final int min_value;
    private final int max_value;

    public HandleDirectoryTask(View view, File startDir, int min_value, int max_value) {
        this.view = view;
        this.startDir = startDir;
        this.min_value = min_value;
        this.max_value = max_value;
    }

    @Override
    public Object call() throws FileNotFoundException {
        File[] files = startDir.listFiles();
        if (files == null) return null;

        for (File file : files) {
            if (file.isFile() && Files.getFileExtension(file.getName()).equals("cs")) {
                searchRows(file);
            }
        }
        return null;
    }

    private void searchRows(File file) throws FileNotFoundException {

        List<String> lines = ReadFileUtility.getLinesOfFile(file);

        for (String line : lines) {
            String[] numbers = line.replaceAll("[^0-9]+", " ").trim().split(" ");

            if (numbers[0].equals("")) continue;

            for (String number : numbers) {
                double value = Double.parseDouble(number);
                if (value >= min_value && value <= max_value) {
                    view.printMessageLn(file.getPath() + line);
                    break;
                }
            }
        }

    }

}

