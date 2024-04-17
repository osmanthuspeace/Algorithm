package org.example;

import java.util.Stack;

/**
 * author: osmanthuspeace
 * createTime: 2024/4/17
 */
public class Solution {

    //20:匹配括号
    public static boolean isValidParentheses(String s) {
        Stack<Character> parentheses = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            var parenthesis = s.charAt(i);
            if (parenthesis == '(' || parenthesis == '[' || parenthesis == '{')
                parentheses.push(parenthesis);
            else {
                if (parentheses.isEmpty())
                    return false;
                char top = parentheses.pop();
                if (top == '{' && parenthesis != '}') return false;
                if (top == '[' && parenthesis != ']') return false;
                if (top == '(' && parenthesis != ')') return false;
            }
        }
        return parentheses.isEmpty();
    }


    //232:用栈实现队列
    public static class MyQueue {
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


    private static class Node {
        int current;
        Node next;

        public Node() {
        }

        public Node(int current, Node next) {
            this.current = current;
            this.next = next;
        }
    }

    private static class CircularLinkedList {
        Node first = new Node(1, null);
        private int capacity;

        public CircularLinkedList(int n) {
            this.capacity = n;
            Node pre = first;
            for (int i = 2; i <= n; i++) {
                Node newNode = new Node(i, null);
                pre.next = newNode;//让pre节点的下一个节点成为newNode
                pre = newNode;
            }
            pre.next = first;
        }

        public void printElement() {
            Node cur = first;
            for (int i = 0; i < capacity; i++) {
                System.out.println(cur.current);
                cur = cur.next;
            }
        }

        public void pop(int counter) {
            Node cur = first;
            while (!isEmpty()) {
                for (int i = 1; i < counter - 1; i++) {
                    cur = cur.next;
                }
                System.out.print(cur.next.current + " ");
                cur.next = cur.next.next;
                cur = cur.next;
                capacity--;
            }
        }

        private boolean isEmpty() {
            return capacity == 0;
        }
    }

    //P1996:约瑟夫问题
    public static void Josephus(int n, int m) {
        var circular = new CircularLinkedList(n);
        circular.pop(m);
    }


    //547:动态连通性,并查集
    public int findCircleNum(int[][] isConnected) {

        var length = isConnected.length;
        var UF = new UnionFind(length);

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (isConnected[i][j] == 1) {
                    UF.union(i, j);
                }
            }
        }
        return UF.count();

    }

    public static class UnionFind {
        private final int[] parent;
        private int count;
        private final int[] size;

        public UnionFind(int N) {

            this.count = N;
            this.parent = new int[N];
            this.size = new int[N];
            for (int i = 0; i < N; i++) {
                this.parent[i] = i;
                this.size[i] = 1;
            }
        }

        public int count() {
            return this.count;
        }

        public void union(int p, int q) {

            int pRoot = find(p);
            int qRoot = find(q);
            if (pRoot == qRoot) return;
            if (size[pRoot] > size[qRoot]) {
                parent[qRoot] = pRoot;
                size[pRoot] += size[qRoot];
            } else {
                parent[pRoot] = qRoot;
                size[qRoot] += size[pRoot];
            }
            count--;
        }

        //断言p是合法的
        public int find(int p) {
            while (p != parent[p]) {
                p = parent[p];
            }
            return p;
        }
    }

}
