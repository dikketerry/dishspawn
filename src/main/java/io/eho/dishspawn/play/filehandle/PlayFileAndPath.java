package io.eho.dishspawn.play.filehandle;

import java.io.File;
import java.io.IOException;

public class PlayFileAndPath {

    public static void main(String[] args) throws IOException {

        File file = new File("/Users/erikh/javatest/test.txt");
        printPaths(file);

    }

    private static void printPaths(File file) throws IOException {
        System.out.println("Absolute path: " + file.getAbsolutePath());
        System.out.println("Relative path: " + file.getCanonicalPath());
        System.out.println("Path: " + file.getPath());
    }
}
