package org.example.Sort;

import java.util.Arrays;

import static java.lang.Math.min;


/**
 * author: osmanthuspeace
 * createTime: 2024/4/19
 */
@SuppressWarnings({"rawtypes", "unchecked", "RedundantSuppression"})
public class MyMergeSort<T extends Comparable<T>> {

    private final T[] aux;

    public MyMergeSort(T[] a) {
        aux = Arrays.copyOf(a, a.length);
    }

    public void mergeSort(T[] a, int lo, int hi) {
        if (lo >= hi) return;
        int mid = (lo + hi) / 2;
        mergeSort(a, lo, mid);
        mergeSort(a, mid + 1, hi);
        merge(a, lo, mid, hi);
    }

    public void BottomUpMergeSort(T[] a) {
        for (int size = 1; size < a.length; size += size) {
            for (int i = 0; i < a.length - size; i += (size * 2)) {
                merge(a, i, i + size - 1, min(i + size * 2 - 1, a.length - 1));
            }
        }
//        System.out.println(Arrays.toString(a));
    }

    public void mergeSortOptimizedByInsertion(T[] a, int lo, int hi) {
        if (lo >= hi) return;
        if ((hi - lo) < 7) {
            insertion(a, lo, hi);
            return;
        }
        int mid = (lo + hi) / 2;
        mergeSort(a, lo, mid);
        mergeSort(a, mid + 1, hi);
        if (!less(a[mid], a[mid + 1]))
            merge(a, lo, mid, hi);
    }

    //此时的a[hi]是需要参与比较的
    private void insertion(T[] a, int lo, int hi) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo && less(a[j], a[j - 1]); j--) {
                T t = a[j - 1];
                a[j - 1] = a[j];
                a[j] = t;
            }
        }
    }

    //a[lo]和a[hi]都可以取到
    private void merge(T[] a, int lo, int mid, int hi) {

        //实时的更新临时数组
        if (hi + 1 - lo >= 0) System.arraycopy(a, lo, aux, lo, hi + 1 - lo);

        //j是第二个数组的起始索引
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (less(aux[i], aux[j])) a[k] = aux[i++];
            else a[k] = aux[j++];
        }
    }

    private boolean less(T v, T w) {
        return v.compareTo(w) < 0;
    }

}
