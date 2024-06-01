package org.example.DataStructure;

import java.util.Arrays;

/**
 * author: osmanthuspeace
 * createTime: 2024/4/21
 */
@SuppressWarnings("unchecked")
//大根堆
//添加了索引功能
public class MyPriorityQueue<E extends Comparable<E>> {

    //无论是否有索引，元素在E[]数组中都必须保持大根堆的顺序
    //索引优先队列维护的是索引与堆位置的双向映射，通过索引，可以在 O(1) 时间内获取元素的当前优先级
    //pq数组是从第一位开始一个一个往后存的，但是element数组中元素的索引是跟随pq数组的
    //即将原来(从一开始递增的正常索引)映射到了自定的任意索引上

    private static final int DEFAULT_SIZE = 11;
    private final int[] pq;//在索引优先队列中表示索引，pq[i] 表示二叉堆中第 i 个位置存储的元素在E[]数组中的索引
    private final int[] qp;//索引的逆序:pq[qp[i]]=qp[pq[i]]=i，表示元素索引 i 在 pq 数组中的位置（即元素 i 在堆中的位置）
    private E[] element;//存放元素
    private int size = 0;//队列中的元素个数

    public MyPriorityQueue() {
        this(DEFAULT_SIZE);
    }

    public MyPriorityQueue(int capacity) {
        element = (E[]) new Comparable[capacity + 1];//不用p[0]
        pq = new int[capacity + 1];
        qp = new int[capacity + 1];
        Arrays.fill(qp, -1);
    }

    public void insert(int index, E v) {
        if (size + 1 > element.length) {
            element = Arrays.copyOf(element, element.length + element.length >> 1);//扩大原来的一半
        }
        size++;
        pq[size] = index;
        qp[index] = size;//索引的值一定要小于队列的容量
        element[index] = v;
        swim(size);
    }

    public E delMax() {
        int indexOfMax = pq[1];
        E max = element[1];
        exchange(1, size);
        size--;
//        element[size] = null;
        sink(1);
        element[pq[size + 1]] = null;//消除最后一个元素的引用，防止对象游离
        qp[pq[size + 1]] = -1;//消除最后一个元素的索引
        pq[size + 1] = -1; // 清除pq数组中最后一个元素的位置
        return max;
    }

    public E max() {
        return element[pq[1]];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    //检测某一索引是否有对应的值
    public boolean contains(int k) {
        return qp[k] != -1;
    }

    public int size() {
        return this.size;
    }

    //使位置k的元素上浮（自下而上的堆的有序化）
    private void swim(int k) {
        //如果该节点大于父节点，要维护堆的顺序，就要使该元素上浮
        while (k > 1 && less(k / 2, k)) {
            exchange(k, k / 2);
            k = k / 2;
        }
    }

    //使位置k的元素下沉（自上而下的堆的有序化）
    private void sink(int k) {
        //如果该节点小于子节点，要维护堆的顺序，就要使该元素下沉
        while (2 * k <= size) {
            int j = 2 * k;
            if (j < size && less(j, j + 1)) j++;// 保证从下一层换上来的较大元素也一定比下一层的兄弟节点大
            if (!less(k, j)) break;
            exchange(k, j);
            k = j;
        }
    }

    private boolean less(int i, int j) {
        return element[pq[i]].compareTo(element[pq[j]]) < 0;
    }

    private void exchange(int i, int j) {
        int temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
        qp[pq[i]] = i;
        qp[pq[j]] = j;

    }

    public static void main(String[] args) {
        var test = new MyPriorityQueue<Integer>(101);
        test.insert(50, 3);
        test.insert(89, 4);
        test.insert(98, 1);
        test.insert(11, 0);
        System.out.println(test.max());
    }
}
