package org.example.Utility;

import edu.princeton.cs.algs4.StdDraw;

/**
 * author: osmanthuspeace
 * createTime: 2024/4/17
 */
public class TestDraw {
    public static void main(String[] args) {
        int N = 100;
        StdDraw.setXscale(1, N);
        StdDraw.setYscale(1, N * N);
        StdDraw.setPenRadius(0.01);
        for (int i = 0; i < N; i++) {
            StdDraw.point(i, i);
            StdDraw.point(i, i * i);
        }
    }
}
