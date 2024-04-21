package org.example;


import edu.princeton.cs.algs4.MaxPQ;

import java.util.Arrays;

import static org.example.Sort.MySorts.BetterQuickSort;
import static org.example.Sort.MySorts.QuickSort;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        var a = new Integer[]{2, 2, 1, 1, 1};
        BetterQuickSort(a);
        System.out.println(Arrays.toString(a));

    }
}