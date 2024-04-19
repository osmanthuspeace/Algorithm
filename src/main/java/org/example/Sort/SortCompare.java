package org.example.Utility;

/**
 * author: osmanthuspeace
 * createTime: 2024/4/18
 */

import edu.princeton.cs.algs4.*;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

import static org.example.MySorts.*;

public class SortCompare {

    public static double time(String alg, Double[] a) {
        var sw = new Stopwatch();
        switch (alg) {
            case "Insertion" -> Insertion.sort(a);
            case "InsertionX" -> InsertionX.sort(a);
            case "BinaryInsertion" -> BinaryInsertion.sort(a);
            case "Selection" -> Selection.sort(a);
            case "Shell" -> Shell.sort(a);
            case "Merge" -> Merge.sort(a);
            case "MergeX" -> MergeX.sort(a);
            case "MergeBU" -> MergeBU.sort(a);
            case "Quick" -> Quick.sort(a);
            case "Quick3way" -> Quick3way.sort(a);
            case "QuickX" -> QuickX.sort(a);
            case "Heap" -> Heap.sort(a);
            case "System" -> Arrays.sort(a);
            case "MyInsertion" -> InsertSort(a);
            case "MyShell" -> ShellSort(a);
            case "MyMerge" -> MergeSort(a);
            case "MyBetterMerge" -> MergeSortOptimizedByInsertion(a);
            case "MyMergeBU" -> BottomUpMergeSort(a);
            default -> throw new IllegalArgumentException("Invalid algorithm: " + alg);
        }
        return sw.elapsedTime();
    }

    // Use alg to sort trials random arrays of length n.
    public static double timeRandomInput(String alg, int n, int trials) {
        double total = 0.0;
        Double[] a = new Double[n];
        // Perform one experiment (generate and sort an array).
        for (int t = 0; t < trials; t++) {
            for (int i = 0; i < n; i++)
                a[i] = StdRandom.uniformDouble(1.0, 10.0);
            total += time(alg, a);
        }
        return total;
    }

    // Use alg to sort trials random arrays of length n.
    public static double timeSortedInput(String alg, int n, int trials) {
        double total = 0.0;
        Double[] a = new Double[n];
        // Perform one experiment (generate and sort an array).
        for (int t = 0; t < trials; t++) {
            for (int i = 0; i < n; i++)
                a[i] = 1.0 * i;
            total += time(alg, a);
        }
        return total;
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        String alg1 = s.next();
        String alg2 = s.next();
        int n = s.nextInt();
        int trials = s.nextInt();
        String option = s.next();
        double time1, time2;
        if (Objects.equals(option, "s")) {
            time1 = timeSortedInput(alg1, n, trials);   // Total for alg1.
            time2 = timeSortedInput(alg2, n, trials);   // Total for alg2.
        } else if (Objects.equals(option, "r")) {
            time1 = timeRandomInput(alg1, n, trials);   // Total for alg1.
            time2 = timeRandomInput(alg2, n, trials);   // Total for alg2.
        } else {
            throw new IllegalArgumentException();
        }

        StdOut.printf("For %d random Doubles and %d trials\n    %s is", n, trials, alg1);
        double ratio = time2 / time1;
        double epsilon = 0.1;
        if (Math.abs(ratio - 1.0) < epsilon)
            StdOut.printf(" almost as fast as %s\n", alg2);
        else if (ratio > 1.0)
            StdOut.printf(" %.1f times faster than %s\n", ratio, alg2);
        else
            StdOut.printf(" %.1f times shower than %s\n", time1 / time2, alg2);

    }
}

