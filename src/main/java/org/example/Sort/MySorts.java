package org.example;

import edu.princeton.cs.algs4.StdIn;
import org.example.Utility.MyMergeSort;

import java.util.Arrays;


/**
 * author: osmanthuspeace
 * createTime: 2024/4/17
 */

public class MySorts {


    //基本类型的包装类基本都实现了Comparable<T>接口
    public static <T extends Comparable<T>> void InsertSort(T[] a) {
        var length = a.length;

        for (int i = 1; i < length; i++) {
            //将a[i]插入到a[0]~a[i-1]之中，"插入"的本质是通过一个接一个交换实现的
            //快就快在一旦有序，就跳出内层循环
            //这种做法可以保证a[i]之前的元素是有序的
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
                exchange(a, j, j - 1);
            }
        }
    }

    public static <T extends Comparable<T>> void ShellSort(T[] a) {
        int length = a.length;
        var amplification = 3;//增幅
        var h = 1;
        //让数组中任意间隔为h的元素都是有序的
        while (h < length / amplification) h = h * amplification + 1;
        while (h >= 1) {
            //将数组变为h有序
            for (int i = h; i < length; i++) {
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {
                    exchange(a, j, j - h);
                }
            }
            h /= amplification;
        }
    }

    public static <T extends Comparable<T>> void MergeSort(T[] a) {
        var m = new MyMergeSort<>(a);
        m.mergeSort(a, 0, a.length - 1);
    }

    public static <T extends Comparable<T>> void MergeSortOptimizedByInsertion(T[] a) {
        var m = new MyMergeSort<>(a);
        m.mergeSortOptimizedByInsertion(a, 0, a.length - 1);
    }

    public static <T extends Comparable<T>> void BottomUpMergeSort(T[] a) {
        var m = new MyMergeSort<>(a);
        m.BottomUpMergeSort(a);
    }

    //v<w
    private static <T extends Comparable<T>> boolean less(T v, T w) {//T 表示一个具体的类型，该类型实现了 Comparable<T>，
        return v.compareTo(w) < 0; // 正确且类型安全的调用
    }

    private static <T extends Comparable<T>> void exchange(T[] a, int i, int j) {
        T t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static void show(Comparable<?>[] a) {
        for (var i : a)
            System.out.print(i + " ");
        System.out.println();
    }

    public static <T extends Comparable<T>> boolean isSorted(T[] a) {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i - 1]))
                return false;
        return true;
    }

    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        assert isSorted(a) : "无序";
        show(a);
    }

}
