package org.example;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * author: osmanthuspeace
 * createTime: 2024/4/17
 */
//The solutions of leetcode or luogu
@SuppressWarnings("unused")
public class Solution {

    //704:二分查找
    public int search(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            if (nums[mid] < target) lo = mid + 1;
            else if (nums[mid] > target) hi = mid - 1;
            else return mid;
        }
        return -1;
    }

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

    //27. 移除元素（直接的想法，但是空间复杂度太大）
    public int removeElement(int[] nums, int val) {
        int count = 0;
        int[] temp = Arrays.copyOf(nums, nums.length);
        for (int i = 0; i < nums.length; i++) {
            if (temp[i] == val) {
                temp[i] = -1;
            } else {
                count++;
            }
        }
        for (int i = 0, j = 0; i < temp.length; i++) {
            if (temp[i] != -1) {
                nums[j++] = temp[i];
            }
        }
        return count;
    }

    //26. 删除有序数组中的重复项（尝试使用双指针的思想）
    public int removeDuplicates(int[] nums) {
        int keep = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[keep]) {
                nums[++keep] = nums[i];//将去重过后的元素填入数组的开头
            }
        }
        return keep;
    }

    //169:摩尔投票法
    public int majorityElement(int[] nums) {
        //第一想法是哈希，但是nums[i]很大，不实用
        //另外注意到出现次数大于 ⌊ n/2 ⌋ 的元素一定只有一个，且排序之后一定在中间，但是时间复杂度高
        if (nums.length == 1) return nums[0];
        int major = nums[0];
        int count = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == major) count++; //票数加一
            else {
                if (count == 0) major = nums[i];//票没了，换一个数
                else count--;//用不同的数字抵消票数
            }
        }
        return major;
    }

    //229:推广到k的摩尔投票法
    public List<Integer> majorityElement2(int[] nums, int k) {
        //出现次数超过n/k的数最多只有k-1个
        if (nums.length == 1) return List.of(nums[0]);
        int[] count = new int[k - 1];
        int[] major = new int[k - 1];
        Arrays.fill(major, Integer.MIN_VALUE);//用不可能的数初始化
        Arrays.fill(count, 0);
        for (int cur : nums) {
            boolean settled = false;
            for (int j = 0; j < k - 1; j++) {
                if (major[j] == cur) {
                    count[j]++;
                    settled = true;
                    break;//对于每一个cur，只要做一次操作：票数++；替换为major；抵消，三种之一，不能重复操作，否则会造成正确的结果被覆盖结果
                }
            }
            if (!settled) {
                for (int j = 0; j < k - 1; j++) {
                    if (count[j] == 0) {//count中为0的就是应该要被替换的元素
                        major[j] = cur;
                        count[j] = 1;
                        settled = true;
                        break;
                    }
                }
            }
            if (!settled) {
                for (int j = 0; j < k - 1; j++) {
                    count[j]--;//每一个不同的元素要抵消目前所有的major一票
                }
            }
        }

        return getResult(nums, k, major);
    }

    private static @NotNull List<Integer> getResult(int[] nums, int k, int[] major) {
        Map<Integer, Integer> actualCounts = new HashMap<>();
        //检查major中是不是都是满足出现次数大于n/k的元素
        for (int num : nums) {
            for (int candidate : major) {
                if (num == candidate) {
                    actualCounts.put(num, actualCounts.getOrDefault(num, 0) + 1);
                    break;
                }
            }
        }

        List<Integer> result = new ArrayList<>();
        final int threshold = nums.length / k;
        for (Map.Entry<Integer, Integer> entry : actualCounts.entrySet()) {
            if (entry.getValue() > threshold) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    //215. 数组中的第K个最大元素
    public int findKthLargest(int[] nums, int k) {
        int[] p = new int[k + 1];
        Arrays.fill(p, Integer.MAX_VALUE);
        int size = 0;//队列中的元素个数
        int N = nums.length;
        //先把前k个元素放进堆里并有序化，建立小根堆
        for (int i = 0; i < k; i++) {
            p[++size] = nums[i];
            swim(p, i + 1);//将原数组索引和队列中的索引对应
        }
        //把更大的元素放入堆中
        for (int i = k; i < N; i++) {
            if (nums[i] > p[1]) {
                p[1] = nums[i];
                sink(p, 1, size);
            }
        }
        for (var i : p) {
            System.out.println(i);
        }
        return p[1];//堆顶的元素是最小的
    }

    private void swim(int[] p, int k) {
        while (k > 1 && p[k / 2] > p[k]) {
            exchange(p, k, k / 2);
            k /= 2;
        }
    }

    @SuppressWarnings("SameParameterValue")
    private void sink(int[] p, int k, int size) {
        while (2 * k <= size) {
            int j = 2 * k;
            if (j < size && p[j] > p[j + 1]) j++;
            if (p[j] < p[k]) exchange(p, j, k);
            k = j;
        }
    }


    //1122. 数组的相对排序
    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        int max = 0;
        var result = new ArrayList<Integer>();
        for (int value : arr1)
            if (value > max)
                max = value;
        int[] count = new int[max + 1];
        for (int j : arr1) count[j]++;
        //以上为计数排序的预处理操作
        //按相对位置将数放入答案数组中
        for (int j : arr2) {
            while (count[j] > 0) {
                result.add(j);
                count[j]--;
            }
        }
        //将剩下的元素按照升序放在答案数组的末尾
        for (int i = 0; i < count.length; i++) {
            while (count[i] > 0) {
                result.add(i);
                count[i]--;
            }
        }
        //将ArrayList转换为int[]
        int[] resultArray = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            resultArray[i] = result.get(i); // 自动拆箱将Integer转换为int
        }
        return resultArray;
    }


    //56. 合并区间
    public int[][] mergeRanges(int[][] intervals) {
        if (intervals.length <= 1) return intervals;
        int[][] result = new int[intervals.length][2];
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));//先按首元素排序
        result[0] = intervals[0];//初始化为原数组第一项
        int j = 0;//结果数组的索引，也是一种双指针的思想
        for (int i = 1; i < intervals.length; i++) {
            if (result[j][1] >= intervals[i][0]) {//有重叠
                result[j][1] = Math.max(intervals[i][1], result[j][1]);//防止出现前一个区间把后一个区间完全包住的情况
            } else {//无重叠
                j++;
                result[j] = intervals[i];
            }
        }
        return Arrays.copyOf(result, ++j);//拷贝数组，防止结果的末尾出现空的元素
    }


    public static void main(String[] args) {
        int[][] test = new int[][]{{1, 10}, {2, 3}, {4, 5}, {6, 7}, {8, 9}};
        int[] a = new int[]{2, 3, 1, 3, 2, 4, 6, 7, 9, 2, 19};
        int[] a2 = new int[]{2, 1, 4, 3, 9, 6};
        var s = new Solution();
        System.out.println(Arrays.deepToString(s.mergeRanges(test)));

    }

}
