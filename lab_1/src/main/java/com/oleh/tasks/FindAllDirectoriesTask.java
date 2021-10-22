package com.oleh.tasks;

import com.oleh.view.View;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

public class FindAllDirectoriesTask implements Callable<Object> {

    private final View view;
    private final BlockingQueue<File> dirs;
    private final File start_dir;

    public FindAllDirectoriesTask(View view, BlockingQueue<File> dirs, File start_dir) {
        this.view = view;
        this.start_dir = start_dir;
        this.dirs = dirs;
    }

    @Override
    public Object call() {
        findSubDirectories(start_dir);
        putDirIntoQueue(new File(""));
        return null;
    }

    private void findSubDirectories(File dir) {

        if (dir.isDirectory()) {
            putDirIntoQueue(dir);
        }

        File[] files = dir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                findSubDirectories(file);
            }
        }

    }

    private void putDirIntoQueue(File dir) {
        try {
            dirs.put(dir);
        } catch (InterruptedException e) {
            view.printMessageLn(String.format(View.DIR_HAS_BEEN_SKIPPED, dir));
        }
    }


}
