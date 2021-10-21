package com.oleh.tasks;

import com.google.common.io.Files;
import com.oleh.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
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
    public Object call() {
        File[] files = startDir.listFiles();
        if (files == null) return null;

        for (File file : files) {
            if (file.isFile() && Files.getFileExtension(file.getName()).equals("cs")) {
                searchRows(file);
            }
        }
        return null;
    }

    private void searchRows(File file) {
        Scanner sc = null;
        try {
            sc = new Scanner(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (true) {
            assert sc != null;
            if (!sc.hasNextLine()) break;
            String str = sc.nextLine();

            String[] numbers = str.replaceAll("[^0-9]+", " ").trim().split(" ");

            if (numbers[0].equals("")) continue;

            for (String number : numbers) {
                double value = Double.parseDouble(number);
                if (value >= min_value && value <= max_value) {
                    view.printMessageLn(file.getPath() + str);
                    break;
                }
            }
        }
        sc.close();
    }

}

