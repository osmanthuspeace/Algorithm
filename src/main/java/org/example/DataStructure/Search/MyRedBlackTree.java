package org.example.DataStructure.Search;

import lombok.Getter;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/5
 */
@Getter
@SuppressWarnings("unused")
public class MyRedBlackTree<Key extends Comparable<Key>, Val> {

    private static final boolean RED = true;//如果指向一个节点的链接是红色的，那么该节点为红色
    private static final boolean BLACK = false;//约定空链接为黑色
    /*
        类比2-3树，红色节点表示该节点是一个3-节点的一部分，而一个黑色节点表示它是一个普通的2-节点
        当有一个节点的两个子链接都是红色，说明形成了一个4-节点，此时就要将中间的节点提升到它的根节点处
        红黑树的平衡是通过确保任何从根到叶的路径上黑色节点的数量相同来实现的
        将根节点保持为黑色有助于简化这种平衡的维护
     */
    /*
        多数红黑树的实现是左倾的，即保证红链接都是左链接
        插入新节点时，允许红色链接暂时出现在右边（形成一个临时的4-节点），然后通过左旋调整
        维持左倾只是为了简化操作和维持树的平衡，也可以使用友情的红黑树，只要不混用就行
     */

    private Node root;

    @Getter
    @SuppressWarnings("FieldMayBeFinal")
    public class Node {
        private Key key;
        private Val val;         // associated data
        private Node left;
        private Node right;
        private int N;          // subtree count
        private boolean color;     // color of parent link


        public Node(Key key, Val val, int N, boolean color) {
            this.key = key;
            this.val = val;
            this.color = color;
            this.N = N;
        }

        public boolean getColor() {
            return this.color;
        }
    }

    public void put(Key key, Val val) {
        root = put(root, key, val);
        root.color = BLACK;//保持根节点是黑色的
    }

    private Node put(Node h, Key key, Val val) {
        if (h == null) return new Node(key, val, 1, RED);//标准的插入操作，默认和父节点用红链接相连（如果用黑色连接的话，就和普通的二叉查找树一样了）

        int cmp = key.compareTo(h.key);
        if (cmp < 0) h.left = put(h.left, key, val);
        else if (cmp > 0) h.right = put(h.right, key, val);
        else h.val = val;

        if (!isRed(h.left) && isRed(h.right))
            h = rotateLeft(h);//保证红链接一定是左链接
        if (isRed(h.left) && isRed(h.left.left))
            h = rotateRight(h);//当有连续两条红链接时，右旋维持树的平衡
        if (isRed(h.left) && isRed(h.right))
            flipColors(h);//在处理完上面两种情况之后，当左右两个链接都是红链接时，进行颜色转换

        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }

    /*
        类比2-3树的删除：
            一个3-节点包含两个键，删除一个键后不会导致空节点，因此删除相对简单
            而一个2-节点仅包含一个键，删除这个唯一的键会产生空节点，需要通过从兄弟或父节点借用键来修复树的结构，因此更麻烦
        即，在删除过程的最后，我们需要最终得到一个含有最小键的3-节点或4-节点（即被删节点无子节点，且被删结点为红色），然后直接删除最小键
        为此，要尽可能的消除2-节点

     */
    public void deleteMin() {
        if (!isRed(root.left) && !isRed(root.right)) {
            //当根节点的两个子节点都是2-节点时，将根节点变为红色，确保根节点不会作为2-节点被特殊处理
            //删除操作过程中，树中的黑色节点需要进行调整，但是根节点是特殊的，它的颜色不会影响黑色平衡性，也不需要担心父节点颜色的问题，因此简化这种情况
            root.color = RED;
        }
        root = deleteMin(root);
        if (!isEmpty())
            root.color = BLACK;
    }

    //
    private Node deleteMin(Node h) {
        if (h.left == null) return null;//发现最小节点
        if (!isRed(h.left) && !isRed(h.left.left)) {
            //此时h.left一定是一个2-节点（左倾的红黑树右节点一定是黑的）
            //而h一定是红色的，否则在上一次递归中就会被处理
            //需要从右子树借一个红色链接过来以保持下一个当前节点不是2-节点
            h = moveRedLeft(h);
        }
        h.left = deleteMin(h.left);
        return balance(h);
    }

    public void deleteMax() {
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;
        root = deleteMax(root);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node deleteMax(Node h) {
        if (isRed(h.left))
            h = rotateRight(h);
        if (h.right == null)
            return null;
        if (!isRed(h.right) && !isRed(h.right.left))
            h = moveRedRight(h);
        h.right = deleteMax(h.right);
        return balance(h);
    }

    public void delete(Key key) {
        if (!isRed(root.left) && !isRed(root.right))//方便翻转颜色
            root.color = RED;
        root = delete(root, key);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node delete(Node h, Key key) {
        if (key.compareTo(h.key) < 0) {//要删的节点在当前节点的左侧
            if (!isRed(h.left) && !isRed(h.left.left))
                h = moveRedLeft(h);
            h.left = delete(h.left, key);
        } else {
            if (isRed(h.left))
                h = rotateRight(h);
            if (key.compareTo(h.key) == 0 && (h.right == null))
                return null;
            if (!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);
            if (key.compareTo(h.key) == 0) {//找到待删除节点，和一般的二叉查找树一样做删除
                h.val = get(h.right, min(h.right).key);//找到h的后继节点
                h.key = min(h.right).key;//交换后继节点和h
                h.right = deleteMin(h.right);//将新的右子树链接为删去后继节点的右子树
            } else {
                h.right = delete(h.right, key);
            }
        }
        return balance(h);
    }

    private Node moveRedLeft(Node h) {
        flipColors(h);//将 h 暂时变成一个4-节点
        if (isRed(h.right.left)) {
            //通过旋转操作将右子树的红色链接移动到左子树，以确保左侧的节点有足够的红色链接保持平衡
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
        }
        return h;
    }

    private Node moveRedRight(Node h) {
        flipColors(h);
        if (isRed(h.left.left)) {//如果是红色的，说明左子树中存在可以借用的红色链接
            h = rotateRight(h);
        }
        return h;
    }

    private Node balance(Node h) {
//        if (isRed(h.right)) h = rotateLeft(h);
        if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);
        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size(root);
    }

    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }

    //左旋，返回旋转后子树的根节点
    private Node rotateLeft(Node h) {
        Node x = h.right;//定义x为指向h的右子树的根节点
        h.right = x.left;
        x.left = h;
        x.color = h.color;//使返回的节点保留原来的颜色
        h.color = RED;
        x.N = h.N;
        h.N = size(h.left) + size(h.right) + 1;
        return x;
    }

    //右旋
    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;

        x.color = h.color;
        h.color = RED;

        x.N = h.N;
        h.N = size(h.left) + size(h.right) + 1;
        return x;
    }

    //颜色转换
    private void flipColors(Node h) {
        //必须保证h和他的两个子节点颜色相反
        //当h为红色时，该方法实质上是分解一个4-节点
        //之所以要分解4-节点，一是为了防止插入之后连续的红色节点
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.N;
    }

    public Val get(Key key) {
        if (isEmpty()) return null;
        return get(root, key);
    }

    private Val get(Node h, Key key) {
        while (h != null) {
            int cmp = key.compareTo(h.key);
            if (cmp < 0) h = h.left;
            else if (cmp > 0) h = h.right;
            else return h.val;
        }
        return null;
    }

    public Key min() {
        if (isEmpty()) return null;
        return min(root).key;
    }

    private Node min(Node h) {
        if (h.left == null) return h;
        return min(h.left);
    }
    // 在 MyRedBlackTree 类中添加此方法
    public void printTree() {
        if (root == null) {
            System.out.println("The tree is empty.");
        } else {
            StringBuilder output = new StringBuilder();
            buildTreeString(root, "", true, output);
            System.out.println(output);
        }
    }

    // 辅助方法：递归构建树的输出字符串
    private void buildTreeString(Node node, String prefix, boolean isTail, StringBuilder output) {
        if (node != null) {
            String colorIndicator = node.getColor() ? "R" : "B";
            output.append(prefix).append(isTail ? "└── " : "├── ").append(node.key.toString()).append("(").append(colorIndicator).append(")").append("\n");

            String newPrefix = prefix + (isTail ? "    " : "│   ");
            if (node.right != null || node.left != null) {
                buildTreeString(node.right, newPrefix, false, output);
                buildTreeString(node.left, newPrefix, true, output);
            }
        }
    }


    public static void main(String[] args) {
        var test=new MyRedBlackTree<Integer,Integer>();
        test.put(12,12);
        test.put(1,1);
        test.put(9,9);
        test.put(2,2);
        test.put(0,0);
        test.put(11,11);
        test.put(7,7);
        test.put(19,19);
        test.put(4,4);
        test.put(15,15);
        test.put(18,18);
        test.put(5,5);
        test.put(14,14);
        test.put(13,13);
        test.put(10,10);
        test.put(16,16);
        test.put(6,6);
        test.put(3,3);
        test.put(8,8);
        test.put(17,17);
        test.printTree();
        test.delete(12);
    }
}
/*
    一颗按照[2,1,5,4,3,0]顺序插入的红黑树形成以下结构（2为红色）
        4
       / \
      2   5
     / \
    1   3
*/
