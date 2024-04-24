package org.example.DataStructure;

/**
 * author: osmanthuspeace
 * createTime: 2024/4/21
 */
@SuppressWarnings("unchecked")
//大根堆
public class MyPriorityQueue<Key extends Comparable<Key>> {

    private Key[] p;
    private int size = 0;
    private static final int DEFAULT_SIZE = 11;

    public MyPriorityQueue() {
        this(DEFAULT_SIZE);
    }

    public MyPriorityQueue(int capacity) {
        p = (Key[]) new Comparable[capacity + 1];//不用p[0]
    }


    public void insert(Key v) {
        if (size + 1 > p.length) {
            p = (Key[]) new Comparable[p.length * 2];
        }
        p[++size] = v;
        swim(size);
    }

    public Key delMax() {
        Key max = p[1];
        exchange(1, size--);
        p[size] = null;//消除最后一个元素的引用，防止对象游离
        sink(1);
        return max;
    }

    public boolean isEmpty() {
        return size == 0;
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
