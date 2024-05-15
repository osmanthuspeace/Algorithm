package org.example.DataStructure;

import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.SequentialSearchST;

import java.util.ArrayList;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/9
 */

//基于拉链法的散列表
@SuppressWarnings({"unchecked", "RedundantSuppression"})
public class MySeparateChainingHashST<Key, Val> {

    private int N; //键值对的总数
    private final int M; //散列表的大小
    private final SequentialSearchST<Key, Val>[] st;

    public MySeparateChainingHashST() {
        this(997);
    }

    public MySeparateChainingHashST(int M) {
        this.M = M;
        st = (SequentialSearchST<Key, Val>[]) new SequentialSearchST[M];
        for (int i = 0; i < M; i++)//创建M条链表
            st[i] = new SequentialSearchST<>();
    }

    private int hash(Key key) {
        //0x7fffffff 是一个 32 位的整数掩码，最高位（符号位）是 0，后面的31位都是1
        //与0x7fffffff进行按位与运算就是将负数变成正数，确保索引是正的
        //之所以不用abs，是因为对于最大的正整数abs会返回一个负值
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public Val get(Key key) {
        return st[hash(key)].get(key);
    }

    public void put(Key key, Val val) {
        st[hash(key)].put(key, val);
        N++;
    }

    public Iterable<Key> keys() {
        var res = new ArrayList<Key>();
        for (int i = 0; i < M; i++) {
            for (var k : st[i].keys())
                res.add(k);
        }
        return res;
    }
}
