package org.example;


import edu.princeton.cs.algs4.MaxPQ;


import java.io.IOException;
import java.util.Arrays;
import java.util.PriorityQueue;

import static org.example.Sort.MySorts.*;
import static org.example.Utility.ReadFileAsString.readFileAsString;
import static org.example.Utility.ReadFileAsString.readFileLinesAsString;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        String path = "numbers.txt";
        try {
            var s = readFileLinesAsString(path);
            var temp = s.toArray(new String[0]);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        var s = new Integer[]{4, 1, 2, 3, 5, 2, 6};
        HeapSort(s);
        System.out.println(Arrays.toString(s));
    }
}