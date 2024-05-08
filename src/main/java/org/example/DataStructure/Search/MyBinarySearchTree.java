package org.example.DataStructure.Search;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * author: osmanthuspeace
 * createTime: 2024/4/29
 */
//基于二叉查找树的符号表
public class MyBinarySearchTree<Key extends Comparable<Key>, Val> implements Iterable<Key> {
    private Node root;

    //定义树的节点
    @SuppressWarnings("FieldMayBeFinal")
    private class Node {
        private Key key;
        private Val val;
        private Node left; // 指向左子树的链接
        private Node right; // 指向右子树的链接
        private int N; // 子树中的节点总数

        public Node(Key key, Val val, int N) {
            this.key = key;
            this.val = val;
            this.N = N;
        }
    }

    public int size() {
        return size(root);
    }

    //外部代码通常不能直接访问树的内部节点
    private int size(Node x) {
        if (x == null) return 0;
        else return x.N;
    }

    public Val get(Key key) {
        return get(root, key);
    }

    private Val get(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x.val;
    }

    //对于大量的查找操作，使用循环而不是递归有更好的性能，因为不需要额外的栈空间和调用
    public Val getWithNonRecursion(Key key) {
        Node x = root;
        while (x != null) {
            int cmp = x.key.compareTo(key);
            if (cmp < 0) x = x.right;
            else if (cmp > 0) x = x.left;
            else return x.val;
        }
        return null;
    }

    public void put(Key key, Val val) {
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Val val) {
        //如果不存在该键值，则创建一个以key,val为键值对的新节点插入到子树中
        if (x == null) return new Node(key, val, 1);
        //如果存在，则更新它的值
        int cmp = key.compareTo(x.key);//查找是否存在该键值
        if (cmp < 0) x.left = put(x.left, key, val);//这样写的话，如果该键值存在，返回的也就是x.left本身，如果不存在，就会有一个新的节点连接到x.left上
        else if (cmp > 0) x.right = put(x.right, key, val);//可以想象成沿着树向上爬，一路上重置每一个父节点指向子节点的链接
        else x.val = val;
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public Key min() {
        return min(root).key;
    }

    @Contract(pure = true)
    private @NotNull Node min(Node x) {
        if (x.left == null) return x;
        return min(x.left);
    }

    public Key max() {
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null) return x;
        return x.right;
    }

    //小于等于key的最大键
    public Key floor(Key key) {
        Node x = floor(root, key);
        return x == null ? null : x.key;
    }

    private Node floor(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        //如果key小于根节点，则floor(key)一定在根节点的左子树中
        if (cmp < 0) return floor(x.left, key);
        //如果key大于根节点，当右子树中存在小于等于key的键时，floor(key)才会出现在右子树中，否则floor(key)就是根节点
        Node t = floor(x.right, key);
        return t == null ? x : t;
    }

    public Key ceiling(Key key) {
        Node x = ceiling(root, key);
        return x == null ? null : x.key;
    }

    private Node ceiling(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp > 0) return ceiling(x.right, key);
        Node t = floor(x.left, key);
        return t == null ? x : t;
    }

    //返回排名为k的节点（排名从0开始）
    public Key select(int k) {
        return select(root, k).key;
    }

    private Node select(Node x, int k) {
        if (x == null) return null;
        int t = size(x.left);
        if (t > k) return select(x.left, k);
        else if (t < k) return select(x.right, k - t - 1);
        else return x;
    }

    //返回key节点的排名
    public int rank(Key key) {
        return rank(root, key);
    }

    private int rank(Node x, Key key) {
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return rank(x.left, key);
        else if (cmp > 0) return rank(x.right, key) + size(x.left) + 1;
        else return size(x.left);
    }

    public void deleteMin() {
        root = deleteMin(root);//当根节点本身就是最小节点时，会更新根节点，否则不变
    }

    private Node deleteMin(Node x) {
        if (x.left == null)
            return x.right;//当 x.left 为 null，这表示 x 是最小节点，因此，要删除 x，需要用 x 的右子树替代它，此时的操作都是对以x为根的子树的，return意味着将x的右子树链接回原本x的父节点
        x.left = deleteMin(x.left);//如果 x 有左子节点，递归调用 deleteMin 继续在左子树中查找最小节点
        x.N = size(x.left) + size(x.right) + 1;//+1是加上了节点自己
        return x;//重新链接更新后的子树回其父节点
    }

    public void delete(Key key) {
        root = delete(root, key);
    }

    private Node delete(Node x, Key key) {
        //x的含义是当前正在被操作的节点
        if (x == null) return null;
        //首先要找到key对应的节点
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else {
            //找到待删元素x
            if (x.left == null) return x.right;//要删除的节点只有一个子节点，就直接将它的子节点连回它的父节点
            if (x.right == null) return x.left;
            Node t = x;//将待删元素的引用赋值给t
            //找到待删元素t之后，找到它的后继节点x，即右子树中的最小节点（也可以使用前趋节点，即左子树的最大节点）
            x = min(t.right);//在这行之前，x是待删元素，之后，x代表后继节点
            //重新构建一颗子树，根节点为x，左子树是t的左子树，右子树是t的右子树删去x之后剩下的部分
            x.right = deleteMin(t.right);//必须先链接右子树，如果先链接左子树，会导致右侧deleteMin时将刚刚连上的左子树包括进去，无法正确的删除后继节点
            x.left = t.left;
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;//代表了在删除节点后，经过调整后的子树的根节点
    }

    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    //二叉查找树的范围查找操作
    public Iterable<Key> keys(Key lo, Key hi) {
        var queue = new LinkedBlockingQueue<Key>();
        keys(root, queue, lo, hi);
        return queue;
    }

    //中序遍历
    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
        if (x == null) return;
        int cmpLo = lo.compareTo(x.key);
        int cmpHi = hi.compareTo(x.key);
        if (cmpLo < 0) keys(x.left, queue, lo, hi);
        if (cmpLo <= 0 && cmpHi >= 0) queue.offer(x.key);
        if (cmpHi > 0) keys(x.right, queue, lo, hi);
    }

    @NotNull
    @Override
    public Iterator<Key> iterator() {
        return new BSTIterator();
    }

    //中序遍历
    private class BSTIterator implements Iterator<Key> {

        private final Stack<Node> stack = new Stack<>();

        public BSTIterator() {
            pushLeft(root);
        }

        private void pushLeft(Node x) {
            //将从x节点开始的路径上的所有左子节点推入栈中，使得栈顶总是保持着最小的元素
            while (x != null) {
                stack.push(x);
                x = x.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public Key next() {
            if (!hasNext()) throw new NoSuchElementException();
            Node node = stack.pop();//这是当前最小的未访问节点，如果这个节点有右子树，按照同样的逻辑（先尽可能地向左），将右子树的路径推入栈中
            pushLeft(node.right); // After visiting node, push all the left nodes of the right subtree，左边走到头了，就转向右边的节点
            return node.key;
        }
    }

}
