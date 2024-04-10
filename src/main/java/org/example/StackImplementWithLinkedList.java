package org.example;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

/**
 * author: osmanthuspeace
 * createTime: 2024/4/10
 */
public class StackImplementWithLinkedList<T> implements Iterable<T>{

    private class Node{
        T item;
        Node next;
        public Node(T item,Node next){
            this.item=item;
            this.next=next;
        }
    }
    private Node first;
    private int capacity;
    public boolean isEmpty(){
        return first==null;
    }
    public int size(){
        return capacity;
    }
    public void push(T item){
        Node oldFirst=first;
        first=new Node(item,oldFirst);
        capacity++;
    }
    public T pop(){
        var _item=first.item;
        first=first.next;
        capacity--;
        return _item;
    }
    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<T>{
        private Node current=first;
        @Override
        public boolean hasNext() {
            return current!=null;
        }

        @Override
        public T next() {
            var _item=current.item;
            current=current.next;
            return _item;
        }
    }
}
