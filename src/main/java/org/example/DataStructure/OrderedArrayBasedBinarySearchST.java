package org.example.DataStructure;

/**
 * author: osmanthuspeace
 * createTime: 2024/4/28
 */
//基于有序数组的符号表
@SuppressWarnings("unused")
public class OrderedArrayBasedBinarySearchST<Key extends Comparable<Key>, Val> {
    private Key[] keys;
    private Val[] vals;
    private int N;

    @SuppressWarnings("unchecked")
    public OrderedArrayBasedBinarySearchST(int capacity) {
        keys = (Key[]) new Comparable[capacity];
        vals = (Val[]) new Object[capacity];

    }

    @SuppressWarnings("unchecked")
    private void resize(int max) {
        assert max >= N : "need more space";
        Key[] tempK = (Key[]) new Comparable[max];
        Val[] tempV = (Val[]) new Object[max];
        System.arraycopy(keys, 0, tempK, 0, N);
        System.arraycopy(vals, 0, tempV, 0, N);
        // 更新引用
        vals = tempV;
        keys = tempK;
    }

    public int size() {
        return N;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    //返回小于给定键值的键的数量，当数组是有序的时候可以快速的定位到
    private int rank(Key key) {
        int lo = 0, hi = N - 1;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            int cmp = key.compareTo(keys[mid]);
            if (cmp < 0) hi = mid - 1;
            else if (cmp > 0) lo = mid + 1;
            else return mid;
        }
        return lo;//如果不存在这个键，rank()还是会返回小于它的键的数量
        //查找一个不存在的键时，最后的情况一定是lo=hi-1，而key就在keys[lo]和keys[hi]之间，于是可以得到正确的结果
    }

    public Val get(Key key) {
        if (isEmpty()) return null;
        int i = rank(key);
        if (i < N && keys[i].compareTo(key) == 0) return vals[i];
        else return null;
    }

    public void put(Key key, Val val) {
        int i = rank(key);
        //如果存在这个键
        if (i < N && keys[i].compareTo(key) == 0) {
            vals[i] = val;
            return;
        }
        //如果不存在这个键
        //把所有元素向后移一位
        for (int j = N; j > i; j--) {
            keys[j] = keys[j - 1];
            vals[j] = vals[j - 1];
        }
        if (N == keys.length) resize(2 * keys.length);
        keys[i] = key;
        vals[i] = val;
        N++;
    }

}
