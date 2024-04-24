//package org.example.Utility;
//
///**
// * author: osmanthuspeace
// * createTime: 2024/4/21
// */
//public class KthLargest {
//
//    private int k;
//    private MyPriorityQueue pq;
//
//    public KthLargest(int k, int[] nums) {
//        this.k=k;
//        this.pq=new MyPriorityQueue(nums.length+1);
//        for(var i : nums){
//            pq.insert(i);
//        }
//    }
//
//    public int add(int val) {
//        pq.insert(val);
//        for(int i=1;i<k;i++){
//            pq.delMax();
//        }
//        return pq.delMax();
//    }
//}
//class MyPriorityQueue{
//
//    private int[] p;
//    private int size = 0;
//
//    public MyPriorityQueue(int capacity) {
//        p = new int[capacity + 1];//不用p[0]
//    }
//
//
//    public void insert(int v) {
//        p[++size] = v;
//        swim(size);
//    }
//
//    public int delMax() {
//        int max = p[1];
//        exchange(1, size--);
//        sink(1);
//        return max;
//    }
//
//    public boolean isEmpty() {
//        return size == 0;
//    }
//
//    public int size() {
//        return this.size;
//    }
//
//    //使位置k的元素上浮（自下而上的堆的有序化）
//    private void swim(int k) {
//        //如果该节点大于父节点，要维护堆的顺序，就要使该元素上浮
//        while (k > 1 && less(k / 2, k)) {
//            exchange(k, k / 2);
//            k = k / 2;
//        }
//    }
//
//    //使位置k的元素下沉（自上而下的堆的有序化）
//    private void sink(int k) {
//        //如果该节点小于子节点，要维护堆的顺序，就要使该元素下沉
//        while (2 * k <= size) {
//            int j = 2 * k;
//            if (j < size && less(j, j + 1)) j++;
//            if (!less(k, j)) break;
//            exchange(k, j);
//            k = j;
//        }
//    }
//
//    private boolean less(int i, int j) {
//        return p[i]<p[j];
//    }
//
//    private void exchange(int i, int j) {
//        var temp = p[i];
//        p[i] = p[j];
//        p[j] = temp;
//
//    }
//}
