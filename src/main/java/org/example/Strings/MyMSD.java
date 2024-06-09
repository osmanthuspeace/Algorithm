package org.example.Strings;

/**
 * author: osmanthuspeace
 * createTime: 2024/6/7
 */
//递归的高位优先的基数排序
public class MyMSD {
    private static final int R = 256;//基数
    private static final int M = 10;//小数组的切换阈值
    private static String[] aux;//用临时数组保持排序的稳定性

    //特别注意排序时到达短字符串末尾的情况
    //重载charAt函数，用来将字符串索引转换为数组索引
    private static int charAt(String s, int d) {
        if (d < s.length()) return s.charAt(d) + 1;
        else return 0;//用0索引表示字符串的结尾，即规定字符串的结尾大于任何字符
    }

    public static void sort(String[] a) {
        int N = a.length;
        aux = new String[N];
        sort(a, 0, N - 1, 0);
    }

    //以每个字符串的第d个字符为键将a[lo~hi]排序
    private static void sort(String[] a, int lo, int hi, int d) {
        //必须使用插入排序处理小数组
        if (hi <= lo + M) {
            insertionSort(a, lo, hi, d);
            return;
        }
        int[] count = new int[R + 2];
        //频率统计
        //count[0]未使用，count[1]表示长度为d的字符串的数量(如果a[i]的长度为d，那么charAt就会返回0，加1之后就对应count[1])
        //count[2~R+1]代表的是第d个字符的索引值是r-2的字符串数量
        for (int i = lo; i <= hi; i++) {
            count[charAt(a[i], d) + 1]++;
        }
        //将频率转换为索引
        //count[0]代表的是长度为d的字符串的子数组的起始索引(子数组是指去掉有序的开头部分剩下的子串)，count[0]的值的改变在下一个循环中
        //count[1~R+1]对应的是((第d个字符的索引值)是r-1的)字符串数量
        for (int r = 0; r < R + 1; r++) {
            count[r + 1] += count[r];
        }
        //将元素分类
        //count[0~R-1]对应第d个字符的索引为r的字符串的子数组的起始索引
        for (int i = lo; i <= hi; i++) {
            aux[count[charAt(a[i], d)]++] = a[i];
        }
        //noinspection ManualArrayCopy
        for (int i = lo; i < hi; i++) {
            a[i] = aux[i - lo];//i-lo使得aux数组的索引从0开始
        }
        //递归的以每个字符为键进行排序
        for (int r = 0; r < R; r++) {
            //从lo + count[r] 到 lo + count[r + 1] 是前d个字符都相同的的字符串
            sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1);
        }
    }

    private static void insertionSort(String[] a, int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo && less(a[j], a[j - 1], d); j--) {
                String t = a[j];
                a[j] = a[j - 1];
                a[j - 1] = t;
            }
        }
    }

    private static boolean less(String v, String w, int d) {
        // assert v.substring(0, d).equals(w.substring(0, d));
        // return v.substring(d).compareTo(w.substring(d))<0;
        //从第d个字符开始比较
        for (int i = d; i < Math.min(v.length(), w.length()); i++) {
            if (v.charAt(i) < w.charAt(i)) return true;
            if (v.charAt(i) > w.charAt(i)) return false;
        }
        // 如果前面的字符都相等，比较长度
        return v.length() < w.length();
    }

    public static void main(String[] args) {
        var m = new String[]{"she", "by", "the"};
        sort(m);
    }
}
