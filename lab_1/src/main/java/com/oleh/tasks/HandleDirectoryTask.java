package com.oleh.tasks;

import com.google.common.io.Files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class HandleDirectoryTask implements Runnable {

    private final ExecutorService service;
    private final BlockingQueue<Future<?>> futures;
    private final File startDir;
    private final int min_value;
    private final int max_value;

    public HandleDirectoryTask(File startDir, ExecutorService service, BlockingQueue<Future<?>> futures, int min_value, int max_value) {
        this.startDir = startDir;
        this.service = service;
        this.futures = futures;
        this.min_value = min_value;
        this.max_value = max_value;
    }

    @Override
    public void run() {
        File[] files = startDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                Future<?> future = service.submit(new HandleDirectoryTask(file, service, futures, min_value, max_value));
                try {
                    futures.put(future);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (Files.getFileExtension(file.getName()).equals("cs")) {
                searchRows(file);
            }
        }
    }

    private void searchRows(File file) {
        Scanner sc = null;
        try {
            sc = new Scanner(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (sc.hasNextLine()) {
            String str = sc.nextLine();

            String[] numbers = str.replaceAll("[^0-9]+", " ").trim().split(" ");

            if (numbers[0].equals("")) continue;

            for (String number : numbers) {
                double value = Double.parseDouble(number);
                if (value >= min_value && value <= max_value) {
                    System.out.println(file.getPath() + " -> " + str);
                    break;
                }
            }
        }
        sc.close();
    }

}

