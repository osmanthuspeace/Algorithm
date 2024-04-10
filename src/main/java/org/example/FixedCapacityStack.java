package org.example;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

/**
 * author: osmanthuspeace
 * createTime: 2024/4/9
 */
public class FixedCapacityStack<T> implements Iterable<T> {
    private T[] stack;//不能直接创建泛型数组，但是在构造函数中强制类型转换了
    private int index;

    @SuppressWarnings("unchecked")
    public FixedCapacityStack(int capacity) {
        this.stack = (T[]) new Object[capacity];
        this.index = 0;
    }

    public void push(T item) {
        if (index == stack.length)
            resize(2 * stack.length);
        stack[index++] = item;
    }

    public T pop() {
        var temp = stack[--index];//每一次push之后，index都会往后加一位
        stack[index] = null;//避免对象游离,即清除无用对象的引用,让垃圾回收器回收
        if (index > 0 && index <= stack.length / 4)
            resize(stack.length / 2);
        return temp;
    }

    public boolean isEmpty() {
        return index == 0;
    }

    public int size() {
        return index;
    }

    @SuppressWarnings("unchecked")
    private void resize(int max) {
        T[] temp = (T[]) new Object[max];
        if (index >= 0) System.arraycopy(stack, 0, temp, 0, index);
        stack = temp;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new ReverseArrayIterator();
    }

    private class ReverseArrayIterator implements Iterator<T> {
        private int i = index;

        @Override
        public boolean hasNext() {
            return i > 0;
        }

        @Override
        public T next() {
            return stack[--i];//仅仅会改变i的值，不会改变外部类中的字段
        }
    }
}
