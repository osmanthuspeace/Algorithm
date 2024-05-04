package org.example.DataStructure;

import java.util.Arrays;

/**
 * author: osmanthuspeace
 * createTime: 2024/4/21
 */
@SuppressWarnings("unchecked")
//大根堆
//添加了索引功能
public class MyPriorityQueue<Key extends Comparable<Key>> {

    private Key[] p;//存放元素
    private int size = 0;//队列中的元素个数
    private static final int DEFAULT_SIZE = 11;

    private final int[] pq;//在索引优先队列中表示索引
    private final int[] qp;//索引的逆序:pq[qp[i]]=qp[pq[i]]=i

    public MyPriorityQueue() {
        this(DEFAULT_SIZE);
    }

    public MyPriorityQueue(int capacity) {
        p = (Key[]) new Comparable[capacity + 1];//不用p[0]
        pq = new int[capacity + 1];
        qp = new int[capacity + 1];
        Arrays.fill(qp, -1);
    }


    public void insert(int k, Key v) {
        if (size + 1 > p.length) {
            p = Arrays.copyOf(p, p.length + p.length >> 1);//扩大原来的一半
        }
        size++;
        pq[size] = k;
        qp[k] = size;
        p[size] = v;
        swim(size);
    }

    public Key delMax() {
        int indexOfMax = pq[1];
        Key max = p[1];
        exchange(1, size);
        size--;
//        p[size] = null;
        sink(1);
        p[pq[size + 1]] = null;//消除最后一个元素的引用，防止对象游离
        qp[pq[size + 1]] = -1;//消除最后一个元素的索引
        return max;
    }

    public Key max() {
        return p[pq[1]];
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
        return p[i].compareTo(p[j]) < 0;
    }

    private void exchange(int i, int j) {
        var temp = p[i];
        p[i] = p[j];
        p[j] = temp;

    }
}
