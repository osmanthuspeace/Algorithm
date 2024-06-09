package org.example.Strings;

/**
 * author: osmanthuspeace
 * createTime: 2024/6/9
 */
public class ThreeWaySyncopationStringQuickSort {

    private static int charAt(String s, int d) {
        if (d < s.length()) return s.charAt(d) + 1;
        else return 0;
    }

    public static void sort(String[] a) {
        sort(a, 0, a.length - 1, 0);
    }

    private static void sort(String[] a, int lo, int hi, int d) {
        if (lo >= hi) return;
        int lt = lo, gt = hi;
        int pivot = charAt(a[lo], d);
        int cur = lo + 1;
        while (cur <= gt) {
            int dthIndex = charAt(a[cur], d);//第d位字符的索引
            if (dthIndex < pivot) exchange(a, lt++, cur++);
            else if (dthIndex > pivot) exchange(a, cur, gt--);
            else cur++;
        }
        //切分的结果是a[lo..lt-1] < pivot=a[lt..gt] < a[gt+1..hi]
        sort(a, lo, lt - 1, d);
        if (pivot >= 0) sort(a, lt, gt, d + 1);//当第d位的字符是等于切分元素时，d+1，相当于在结下的排序中忽略首字母
        sort(a, gt + 1, hi, d);
    }

    private static void exchange(String[] a, int i, int j) {
        String t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
