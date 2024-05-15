package org.example.DataStructure;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/9
 */

//基于线性探测（开放地址）的符号表
@SuppressWarnings("unchecked")
public class MyLinearProbingHashST<Key, Val> {

    private int N;
    private int M = 16;
    private Key[] keys;
    private Val[] vals;

    public MyLinearProbingHashST() {
        keys = (Key[]) new Object[M];
        vals = (Val[]) new Object[M];
    }

    public MyLinearProbingHashST(int M) {
        this();
        this.M = M;
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    private void resize(int size) {
        MyLinearProbingHashST<Key, Val> t = new MyLinearProbingHashST<>(size);
        for (int i = 0; i < M; i++)
            if (keys[i] != null)
                t.put(keys[i], vals[i]);
        keys = t.keys;//更新引用
        vals = t.vals;
        M = t.M;
    }

    public void put(Key key, Val val) {
        if (N >= M / 2) resize(2 * M);//当使用率超过1/2，增大线性探测表的大小
        int i = hash(key);
        for (; keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(key)) {
                vals[i] = val;
                return;
            }
        }
        keys[i] = key;
        vals[i] = val;
        N++;
    }

    public Val get(Key key) {
        for (int i = hash(key); keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(key)) {
                return vals[i];
            }
        }
        return null;
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public void delete(Key key) {
        if (!contains(key)) return;
        int i = hash(key);
        while (!key.equals(keys[i]))
            i = (i + 1) % M;
        keys[i] = null;
        vals[i] = null;
        i = (i + 1) % M;
        while (keys[i] != null) {
            Key keyToRedo = keys[i];
            Val valToRedo = vals[i];
            keys[i] = null;
            vals[i] = null;
            N--;
            put(keyToRedo, valToRedo);
            i = (i + 1) % M;
        }
        N--;
        if (N > 0 && N <= M / 8) resize(M / 2);
    }
}
