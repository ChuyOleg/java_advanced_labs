package com.oleh.tasks;

import com.oleh.view.View;

import java.io.File;
import java.util.List;

public class FindAllDirectoriesTask implements Runnable {

    private final View view;
    private final File start_dir;
    private final List<HandleDirectoryTask> tasks;
    private final int min_value;
    private final int max_value;

    public FindAllDirectoriesTask(View view, File start_dir, List<HandleDirectoryTask> tasks, int min_value, int max_value) {
        this.view = view;
        this.start_dir = start_dir;
        this.tasks = tasks;
        this.min_value = min_value;
        this.max_value = max_value;
    }

    @Override
    public void run() {
        findSubDirectories(start_dir);
    }

    private void findSubDirectories(File dir) {

        if (dir.isDirectory()) {
            tasks.add(new HandleDirectoryTask(view, dir, min_value, max_value));
        }

        File[] files = dir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                findSubDirectories(file);
            }
        }

    }


}
