package org.example.Sort;

import edu.princeton.cs.algs4.Insertion;

import java.util.Arrays;

/**
 * author: osmanthuspeace
 * createTime: 2024/4/19
 */
public class MyQuickSort<T extends Comparable<T>> {

    @SuppressWarnings("FieldCanBeLocal")
    private final int M = 7;

    public MyQuickSort() {
    }

    public void QuickSort(T[] a, int lo, int hi) {
        if (lo >= hi) return;
        int pivot = partition(a, lo, hi);
        QuickSort(a, lo, pivot - 1);
        QuickSort(a, pivot + 1, hi);
    }

    //用插入排序处理小数组以避免过多的递归
    //使用三数取中策略
    public void BetterQuickSort(T[] a, int lo, int hi) {

        if (lo >= hi) return;
        if ((hi - lo) < M) {
            Insertion.sort(a, lo, hi + 1);//传入hi+1是为了包括a[length-1]
            return;
        }
        int pivot = partition(a, lo, hi);

        BetterQuickSort(a, lo, pivot - 1);
        BetterQuickSort(a, pivot + 1, hi);
    }

    //用三向切分优化存在重复数字的输入情况
    @SuppressWarnings("unused")
    public void QuickSort3Way(T[] a, int lo, int hi) {
        if (lo >= hi) return;
        int lt = lo, i = lo + 1, gt = hi;
        T pivot = a[lo];
        while (i <= gt) {
            int cmp = a[i].compareTo(pivot);
            if (cmp < 0) exchange(a, lt++, i++);
            else if (cmp > 0) exchange(a, i, gt--);
            else i++;
        }
        /*  当 i > gt 时，循环结束。此时，数组被分成三部分：
            索引从 lo 到 lt-1 的元素都小于 pivot。
            索引从 lt 到 gt 的元素都等于 pivot。
            索引从 gt+1 到 hi 的元素都大于 pivot。
         */
        QuickSort3Way(a, lo, lt - 1);
        QuickSort3Way(a, gt + 1, hi);
    }

    private int partition(T[] a, int lo, int hi) {
        int mid = (hi + lo) / 2; //三数取中
        if (less(a[mid], a[lo])) exchange(a, lo, mid);
        if (less(a[hi], a[lo])) exchange(a, lo, hi);
        if (less(a[hi], a[mid])) exchange(a, mid, hi);

        T pivot = a[mid];
        exchange(a, mid, lo); // 将pivot交换到开始位置
        int i = lo, j = hi + 1;
        while (i < j) {
            //先++，似乎可以保证当数组中存在和切分元素相同的元素的时候，指针也可以正常增减
            while (less(a[++i], pivot)) if (i == hi) break;
            while (less(pivot, a[--j])) if (j == lo) break;//j不可能到lo，因为a[lo]就是pivot，不可能比自己小
            if (i >= j) break;//此时已经有一个位置满足：插入pivot后这个位置就满足切分点的条件
            exchange(a, i, j);
        }
        exchange(a, lo, j);//将pivot交换到切分点的位置
        return j;
    }

    private boolean less(T v, T w) {
        return v.compareTo(w) < 0;
    }

    private void exchange(T[] a, int i, int j) {
        T t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
