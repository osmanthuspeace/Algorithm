package org.example;

import java.util.*;

/**
 * author: osmanthuspeace
 * createTime: 2024/4/17
 */
//The solutions of leetcode or luogu
@SuppressWarnings("unused")
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


    //75:三向切分快速排序
    public void sortColors(int[] nums) {
        sort3(nums, 0, nums.length - 1);
    }

    private void sort3(int[] a, int lo, int hi) {
        int lt = lo, gt = hi, i = lt + 1;
        if (lo >= hi) return;
        int pivot = a[lo];
        while (i <= gt) {
            if (a[i] < pivot) exchange(a, lt++, i++);
            else if (a[i] > pivot) exchange(a, i, gt--);
            else i++;
        }
        sort3(a, lo, lt - 1);
        sort3(a, gt + 1, hi);
    }

    private void exchange(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    //912:快排
    public int[] sortArray(int[] nums) {
        quickSort(nums, 0, nums.length - 1);
        return nums;
    }

    private void quickSort(int[] a, int lo, int hi) {

        if (lo >= hi) return;
        int pivot = partition(a, lo, hi);
        quickSort(a, lo, pivot - 1);
        quickSort(a, pivot + 1, hi);

    }

    private int partition(int[] a, int lo, int hi) {
        int mid = (hi + lo) / 2;
        if (a[lo] > a[mid]) exchange(a, mid, lo);
        if (a[mid] > a[hi]) exchange(a, mid, hi);
        if (a[lo] > a[hi]) exchange(a, lo, hi);
        int i = lo, j = hi + 1;
        int pivot = a[mid];
        exchange(a, lo, mid);
        while (true) {
            while (a[++i] < pivot) if (i == hi) break;
            while (a[--j] > pivot) if (j == lo) break;
            if (i >= j) break;
            exchange(a, i, j);
        }
        exchange(a, lo, j);
        return j;
    }

    //34:二分查找
    public int[] searchRange(int[] nums, int target) {
        if (nums.length == 0) return new int[]{-1, -1};
        int headL = 0, headR = nums.length - 1, headM;
        int endL = 0, endR = nums.length - 1, endM;
        int left = -1, right = -1;
        while (headL < headR) {
            headM = (headL + headR) / 2;
            if (nums[headM] < target) headL = headM + 1;
            else headR = headM; //即使与target相等，也要继续寻找
        }
        if (nums[headL] == target) left = headL;
        while (endL < endR) {
            endM = (endL + endR + 1) / 2; //向上取整，匹配下方的endR=endM-1
            if (nums[endM] <= target) endL = endM;
            else endR = endM - 1;
        }
        if (nums[endR] == target) right = endR;
        return new int[]{left, right};
    }

    //TODO:46. 全排列
//    public List<List<Integer>> permute(int[] nums) {
//
//    }

    //739:单调栈
    public int[] dailyTemperatures(int[] temperatures) {
        var monotone = new Stack<Integer>();//构造单调递增栈
        var result = new int[temperatures.length];
        for (int i = 0; i < temperatures.length; i++) {
            if (monotone.isEmpty()) {
                monotone.push(i);//保存索引，是因为可以通过下标获取元素，但不能通过元素获取下标
            } else {
                var top = monotone.peek();
                while ((!monotone.isEmpty()) && (temperatures[monotone.peek()] < temperatures[i])) {
                    top = monotone.pop();//不需要保存栈的所有元素，将已经有结果的数据弹出，使得每个元素只会入栈和出栈各一次
                    result[top] = i - top;
                }
                monotone.push(i);
            }
        }
        return result;
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    //148. 排序链表
    public ListNode sortList(ListNode head) {

        if (head == null || head.next == null) {
            return head;
        }

        ListNode quick = head, slow = head;
        ListNode prev = null; // 用于记录慢指针的前一个节点，用于切链

        //快慢指针找中间节点
        while (quick != null && quick.next != null) {
            prev = slow;
            slow = slow.next;
            quick = quick.next.next;
        }
        prev.next = null;
        var result1 = sortList(head);
        //返回的是合并后链表的头指针
        var result2 = sortList(slow);
        return mergeList(result1, result2);
    }

    private ListNode mergeList(ListNode l1, ListNode l2) {

        var head = new ListNode(Integer.MIN_VALUE);//哨兵元素，用于保存链表的头的位置，它的next才是链表构建的开始
        var cur = head;//此时cur非空，只是cur.next为null
        while (true) {
            if (l1 == null) {
                cur.next = l2;
                break;

            } else if (l2 == null) {
                cur.next = l1;
                break;

            } else if (l1.val < l2.val) {
                cur.next = l1;
                l1 = l1.next;
            } else {
                cur.next = l2;
                l2 = l2.next;
            }
            cur = cur.next;
        }
        return head.next;
    }


    public static void main(String[] args) {
        int[] aa = new int[]{89, 62, 70, 58, 47, 47, 46, 76, 100, 70};
        int[] a = new int[]{-1, 5, 3, 4, 0};
        var s = new Solution();

        ListNode head = null;
        ListNode l = null;
        for (int value : a) {
            if (head == null) {
                head = new ListNode(value);
                l = head;
            } else {
                l.next = new ListNode(value);
                l = l.next;
            }
        }

        var result = s.sortList(head);

        while (result != null) {
            System.out.print(" " + result.val);
            result = result.next;
        }

    }

}
