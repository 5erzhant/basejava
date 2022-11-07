package com.urise.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\Alexander\\Desktop\\Java\\basejava\\.gitignore";
        File directory = new File("C:\\Users\\Alexander\\Desktop\\Java\\basejava\\src");
        File file = new File("C:\\Users\\Alexander\\Desktop\\Java\\basejava\\.gitignore");
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }
        File dir = new File(".\\src\\com\\urise\\webapp");
        System.out.println(dir.isDirectory());
        File[] list = dir.listFiles();
        if (list != null) {
            for (File f : list) {
                System.out.println(f);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Рекурсивный обход: ");
        recursion(directory);
    }

    public static void recursion(File file) {
        if (file.isDirectory()) {
            System.out.println(file.getName());
            for (File f : Objects.requireNonNull(file.listFiles())) {
                if (f.isDirectory()) {
                    recursion(f);
                } else {
                    System.out.println(f.getName());
                }
            }
        } else {
            System.out.println(file.getName());
        }
    }
}
