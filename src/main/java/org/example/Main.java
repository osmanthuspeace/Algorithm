package org.example;


import java.util.Arrays;

import static org.example.Sort.MySorts.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        var a = new Integer[]{2, 1, 3, 5, 4, 6, 8, 7, 9};
        BottomUpMergeSort(a);
        System.out.println(Arrays.toString(a));
    }
}