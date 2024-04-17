package org.example;

import edu.princeton.cs.algs4.QuickFindUF;
import edu.princeton.cs.algs4.QuickUnionUF;
import edu.princeton.cs.algs4.UF;

import java.util.*;

import static org.example.Algorithm.*;
import static org.example.Utility.FisherYatesShuffle.*;


public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");


        Integer[] arr = new Integer[]{1, 2, 3, 4, 5};
        var result = fisherYatesShuffle(arr);
        System.out.println(result);
    }
}