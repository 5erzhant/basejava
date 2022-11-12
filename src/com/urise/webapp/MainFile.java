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
        recursion(directory, "");
    }

    public static void recursion(File directory, String s) {
        if (directory.isDirectory()) {
            System.out.println(s + directory.getName());
            for (File f : Objects.requireNonNull(directory.listFiles())) {
                if (f.isDirectory()) {
                    recursion(f, s + "  ");
                } else {
                    System.out.println(s + "  " + f.getName());
                }
            }
        } else {
            System.out.println(directory.getName());
        }
    }
}
