package org.example.DataStructure;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * author: osmanthuspeace
 * createTime: 2024/4/28
 */
//基于无序链表的符号表
@SuppressWarnings("unused")
public class LinkedListBasedSequentialSearchSymbolTable<Key, Val> implements Iterable<Key> {
    public LinkedListBasedSequentialSearchSymbolTable() {
    }

    @NotNull
    @Override
    public Iterator<Key> iterator() {
        return new Iterator<>() {
            private Node index = first;

            @Override
            public boolean hasNext() {
                return index.next != null;
            }

            @Override
            public Key next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                var key = index.key;
                index = index.next;
                return key;
            }
        };
    }

    private class Node {
        Key key;
        Val val;
        Node next;

        public Node() {
        }

        public Node(Key key, Val val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    private Node first;
    private int size;

    public Val get(Key key) {
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key))
                return x.val;
        }
        return null;
    }

    public void put(Key key, Val val) {
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                x.val = val;
                return;
            }
        }
        first = new Node(key, val, first);//查找未命中，新建节点
        size++;
    }


    public void delete(Key key) {
        if (first == null) return; // 如果链表为空，则直接返回

        if (first.key.equals(key)) {
            first = first.next; // 如果头节点是要删除的节点，则直接更新头节点
            return;
        }

        Node pre = first;//记录当前节点的前一个节点，pre需要使用first的引用，才能正确的修改链表
        for (Node x = first.next; x != null; x = x.next) {
            if (key.equals(x.key)) {
                pre.next = x.next;
            }
            pre = x;
        }
        size--;
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    //返回表中的所有键
    public Iterable<Key> keys() {
        var result = new ArrayList<Key>();
        for (Node x = first; x != null; x = x.next) {
            result.add(x.key);
        }
        return result;
    }
}