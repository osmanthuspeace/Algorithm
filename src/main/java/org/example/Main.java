package org.example;

import edu.princeton.cs.algs4.BinarySearch;

import java.util.*;

import static org.example.Algorithm.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        var result = BinarySearch.indexOf(new int[]{1, 2, 3, 4, 5}, 3);

        var arr = new int[]{1, 2, 3, 4, 5, -5, 5};
        System.out.println(TwoSumFast(arr));

    }
}