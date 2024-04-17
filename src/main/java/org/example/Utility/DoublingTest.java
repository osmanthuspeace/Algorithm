package org.example.Utility;

import edu.princeton.cs.algs4.*;

/**
 * author: osmanthuspeace
 * createTime: 2024/4/16
 */
public class DoublingTest {
    public static double timeTrial(int N) {
        final int MAX = 100000;
        var a = new int[N];
        for (int i = 0; i < N; i++) {
            a[i] = StdRandom.uniformInt(-MAX, MAX);
        }
        Stopwatch timer = new Stopwatch();
        int cnt = ThreeSumFast.count(a);
        return timer.elapsedTime();
    }

    public static void main(String[] args) {
        for (int N = 250;N<=100000; N += N) {
            double time = timeTrial(N);
            StdOut.printf("%7d %5.1f\n", N, time);
        }
    }
}
