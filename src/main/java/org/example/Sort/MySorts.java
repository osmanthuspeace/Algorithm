package org.example.Sort;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


/**
 * author: osmanthuspeace
 * createTime: 2024/4/17
 */

@SuppressWarnings({"rawtypes", "unchecked", "MismatchedReadAndWriteOfArray", "unused"})
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

    public static <T extends Comparable<T>> void QuickSort(T[] a) {
        StdRandom.shuffle(a);//先打乱数组，使得快排可以发挥更好的性能
        var q = new MyQuickSort<T>();
        q.QuickSort(a, 0, a.length - 1);
    }

    public static <T extends Comparable<T>> void BetterQuickSort(T[] a) {
        var q = new MyQuickSort<T>();
        q.BetterQuickSort(a, 0, a.length - 1);
    }

    public static <T extends Comparable<T>> void HeapSort(T[] a) {
        var h = new MyHeapSort<T>();
        h.heapSort(a);
    }

    //桶排序Demo
    //适用于处理大量分布均匀的数据或者密集型数据，另外由于桶排序可以将数据独立地分配到多个桶中，每个桶可以独立进行排序，因此非常适合并行处理
    //而且桶排序是稳定的
    public static void BucketSort(int[] a) {
        int N = a.length;
        int bucketsCount = 10;
        ArrayList[] buckets = new ArrayList[bucketsCount];
        // 初始化桶
        for (int i = 0; i < bucketsCount; i++) {
            buckets[i] = new ArrayList<>();
        }
        // 计算最大值，以确定映射函数的分母
        int max = Integer.MIN_VALUE;
        max = getMax(a, max);
        int divider = (max / 10) + 1; // 确保最大值也能被映射到有效的桶内
        // 将元素分配到桶中
        for (int value : a) {
            int bucketIndex = mappingFunction(value, divider);
            buckets[bucketIndex].add(value);
        }
        int index = 0;
        for (ArrayList bucket : buckets) {
            if (!bucket.isEmpty()) {
                Collections.sort(bucket);//将每个桶排序
                for (var value : bucket) {//并放回原数组
                    a[index++] = (int) value;
                }
            }
        }
    }

    //映射函数，决定了桶排序的复杂度
    //如果桶分的足够小，理论上时间复杂度是O(n)，但是空间复杂度是O(n*k)，而且可能会有很多空的桶
    private static int mappingFunction(int param, int divider) {
        return param / divider;
    }

    //计数排序，适用于小范围的整数，时间复杂度O(n+k)
    //使用类似哈希的思想，将数组中的每一个元素都转化为键储存在额外的数组中
    public static void CountingSort(int[] a) {
        int max = Integer.MIN_VALUE;
        max = getMax(a, max);
        // 初始化计数数组，长度为最大值加1
        int[] count = new int[max + 1];

        // 对每个元素计数
        //显然，如果10个数中有8个比A小，那么A就应该被放在第9位
        for (int number : a) {
            count[number]++;
        }

        for (int i = 0, index = 0; i < count.length; i++) {
            while (count[i] > 0) {//如果count[i]是0，说明a中没有这个数字，就不会被赋值
                a[index++] = i;
                count[i]--;
            }
        }
    }

    private static int getMax(int[] a, int max) {
        for (int value : a)
            if (value > max)
                max = value;
        return max;
    }

    //基数排序，时间复杂度O(k(n+d))，d为进制，k为最大位数
    //根据数字的每一位来进行排序（从最低位到最高位），对每一位使用稳定的排序算法（如计数排序）
    public static void RadixSort(int[] a) {
        int max = a[0];
        max = getMax(a, max);
        //按位排序直到最大的元素的首位有序，数组中的元素不一定要位数相同，因为高位是0
        //exp表示的是当前的位
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countSortByDigit(a, exp);
        }
    }

    @SuppressWarnings({"ForLoopReplaceableByForEach", "RedundantSuppression"})
    private static void countSortByDigit(int[] arr, int exp) {
        int N = arr.length;
        int[] result = new int[N];
        int[] count = new int[10];//计数数组，显然每一位都是0-9
        int digit;
        for (int i = 0; i < N; i++) {
            digit = (arr[i] / exp) % 10;// 取出当前位置的数
            count[digit]++;
        }
        //因为数组的重新赋值在最后，所以必须先更改count使其包含实际位置信息
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];//此时count[i]等于几，i就应该排在第几位
        }
        for (int i = N - 1; i >= 0; i--) {
            digit = (arr[i] / exp) % 10;
            result[count[digit] - 1] = arr[i];//之所以要-1，是因为数组索引是从0开始的
            count[digit]--;
        }
        System.arraycopy(result, 0, arr, 0, N);
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
        int[] arr = {170, 45, 75, 90, 802, 24, 2, 66};
        RadixSort(arr);
        for (int num : arr) {
            System.out.print(num + " ");
        }
    }

}
