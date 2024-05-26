package org.example.Practice;

import edu.princeton.cs.algs4.SeparateChainingHashST;

import java.util.Arrays;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/14
 */

//针对于大型稀疏矩阵的乘法
@SuppressWarnings("MismatchedReadAndWriteOfArray")
public class SparseVector {
    private final SeparateChainingHashST<Integer, Double> st;

    public SparseVector() {
        st = new SeparateChainingHashST<>();
    }

    public int size() {
        return st.size();
    }

    public void put(int i, double x) {
        st.put(i, x);
    }

    public double get(int i) {
        if (!st.contains(i)) return 0.0;
        else return get(i);
    }

    public double dot(double[] multiplier) {
        double sum = 0.0;
        for (int i : st.keys()) {
            sum += multiplier[i] * this.get(i);//矩阵乘法
        }
        //关键使用a[i].get(j)来代替a[i][j]
        return sum;
    }

    public static void main(String[] args) {
        int N = 10;
        var a = new SparseVector[N];//乘数矩阵
        double[] x = new double[N];//乘数向量
        double[] b = new double[N];//结果
        for (int i = 0; i < N; i++) {
            b[i] = a[i].dot(x);
        }
        System.out.println(Arrays.toString(b));
    }
}
