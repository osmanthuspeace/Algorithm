package org.example.Sort;

/**
 * author: osmanthuspeace
 * createTime: 2024/4/25
 */

public class MyHeapSort<T extends Comparable<T>> {
    public MyHeapSort() {
    }

    public void heapSort(T[] a) {
        int N = a.length;//N是比a的最大索引大一的
        for (int k = N / 2 - 1; k >= 0; k--) {
            sink(a, k, N);
        }//构造有序堆
        while (N > 1) {
            exchange(a, 0, --N);//把最小的换上来，并把N减一，使得最大的元素留在数组末尾
            sink(a, 0, N);//修复堆
        }
    }

    private void sink(T[] a, int k, int N) {
        T temp = a[k]; // 保存开始的元素
        int j;
        while (2 * k + 1 < N) {
            j = 2 * k + 1;// 左子节点
            if (j + 1 < N && less(a[j], a[j + 1])) j++;
            if (!less(a[k], a[j])) break; // 父节点已经大于子节点
//            exchange(a, k, j);
            a[k] = a[j]; // 将较大子节点上移，但是暂时保留父节点的值
            k = j;// 继续下沉检查
        }
        a[k] = temp; // temp移动到最终位置k，避免使用exchange函数
    }

    private boolean less(T v, T w) {
        return v.compareTo(w) < 0;
    }

    @SuppressWarnings("SameParameterValue")
    private void exchange(T[] a, int i, int j) {
        T t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}


