package org.example.DataStructure.Search;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * author: osmanthuspeace
 * createTime: 2024/4/28
 */
//基于有序数组的符号表
@SuppressWarnings("unused")
public class OrderedArrayBasedBinarySearchST<Key extends Comparable<Key>, Val> implements Iterable<Key> {
    private Key[] keys;
    private Val[] vals;
    private int N;//符号表中的元素个数

    @SuppressWarnings("unchecked")
    public OrderedArrayBasedBinarySearchST() {
        keys = (Key[]) new Comparable[11];
        vals = (Val[]) new Object[11];
    }


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

    //返回小于给定键值的键的数量，当数组是有序的时候可以快速的定位到给定键值
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
        //如果存在这个键，修改值
        if (i < N && keys[i].compareTo(key) == 0) {
            vals[i] = val;
            return;
        }
        if (N == keys.length) resize(2 * keys.length);

        //如果不存在这个键，添加新键
        //把所有元素向后移一位
        for (int j = N; j > i; j--) {
            keys[j] = keys[j - 1];
            vals[j] = vals[j - 1];
        }
        keys[i] = key;
        vals[i] = val;
        N++;
    }

    public Key delete(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        if (isEmpty()) return null;
        int i = rank(key);
        // key not in table
        if (i == N || keys[i].compareTo(key) != 0) {
            return null;
        }
        var thisKey = keys[i];
        for (int j = i; j < N - 1; i++) {
            keys[i] = keys[i + 1];
            vals[i] = vals[i + 1];
        }
        N--;
        keys[N] = null;  // to avoid loitering
        vals[N] = null;
        return thisKey;
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public Key minKey() {
        return keys[0];
    }

    public Key maxKey() {
        return keys[N - 1];
    }

    public Key select(int k) {
        return keys[k];
    }

    @NotNull
    @Override
    public Iterator<Key> iterator() {
        return new Iterator<>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < N;
            }

            @Override
            public Key next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                var nextKey = keys[i];
                i++;
                return nextKey;//返回下一个键
            }
        };
    }

}
