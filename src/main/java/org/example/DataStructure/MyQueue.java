package org.example.DataStructure;

import java.util.Stack;

/**
 * author: osmanthuspeace
 * createTime: 2024/4/14
 */
public class MyQueue {
    Stack<Integer> tail;
    Stack<Integer> head;

    public MyQueue() {
        this.head = new Stack<>();
        this.tail = new Stack<>();
    }

    public void push(int x) {
        while (!head.empty()) {
            tail.push(head.pop());
        }
        tail.push(x);
    }

    public int pop() {
        while (!tail.empty()) {
            head.push(tail.pop());
        }
        return head.pop();
    }

    public int peek() {
        while (!tail.empty()) {
            head.push(tail.pop());
        }
        var peek = head.pop();
        head.push(peek);
        return peek;
    }

    public boolean empty() {
        return head.isEmpty() && tail.isEmpty();
    }
}
