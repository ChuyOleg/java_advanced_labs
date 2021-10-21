package com.oleh.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadFileUtility {

    public static List<String> getLinesOfFile(File file) throws FileNotFoundException {

        List<String> lines = new ArrayList<>();

        Scanner scanner = new Scanner(new FileInputStream(file));

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lines.add(line);
        }

        return lines;

    }

}
