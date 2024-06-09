package org.example.DisperseExperiments;

import java.util.*;

/**
 * author: osmanthuspeace
 * createTime: 2024/6/4
 */
public class Experiment4 {

    private final boolean[] marked;
    private final Stack<Integer> path;
    private int vertexCount;//记录遍历到的点的个数，只有vertexCount等于顶点数时，图才是联通的
    private ArrayList<Integer> res;

    //欧拉路：经过图中每条边一次且仅一次的路径
    //半欧拉图：经过图中每条边一次且仅一次的路径，但是起点和终点不同
    public Experiment4(int V, int E) {
        res = new ArrayList<>();
        marked = new boolean[V];
        int[][] adjMatrix = new int[V][V];
        path = new Stack<>();
        var G = new Graph(V);
        var r = new Random();
        for (int e = 0; e < E; e++) {
            int v = r.nextInt(V);
            int w = r.nextInt(V);
            if (v == w || adjMatrix[v][w] == 1) {
                e--;
                continue;
            }
            adjMatrix[v][w] = 1;
            adjMatrix[w][v] = 1;
            G.addEdge(v, w);
        }
        System.out.println("邻接矩阵：");
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                System.out.print(adjMatrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("邻接表：");
        System.out.println(G);
        dfs(G, 0);
        if (vertexCount != V) {
            System.out.println("该图不联通！");
            return;
        }
        int oddCount = 0, end = -1;
        //如果是半欧拉图，记录起点
        int start = -1;
        for (int v = 0; v < V; v++) {
            if (G.degree(v) % 2 == 1) {
                System.out.println("顶点" + v + "度数为奇数");
                oddCount++;
                if (start == -1) {//记录可能的半欧拉图的起点和终点
                    start = v;
                } else if (end == -1) {
                    end = v;
                }
            }
        }
        if (oddCount == 0) {
            System.out.println("该图是欧拉图");
            res = findEulerPath(G, 0);
        } else if (oddCount == 2) {
            System.out.println("该图是半欧拉图");
            res = findEulerPath(G, start);
        } else {
            System.out.println("有" + oddCount + "个顶点的度数为奇数，该图既不是欧拉图也不是半欧拉图");
        }
    }

    public Iterable<Integer> path() {
        return path;
    }

    //判断是否联通
    private void dfs(Graph G, int cur) {
        marked[cur] = true;
        vertexCount++;
        for (var v : G.adj(cur)) {
            if (!marked[v]) {
                dfs(G, v);
            }
        }
    }

    //Hierholzer 算法
    public ArrayList<Integer> findEulerPath(@SuppressWarnings("ClassEscapesDefinedScope") Graph G, int s) {
        Stack<Integer> stack = new Stack<>();//用于dfs并跟踪遍历路径
        stack.push(s);
        while (!stack.isEmpty()) {
            int v = stack.peek();
            if (G.degree(v) == 0) {//意味着已经访问了v的所有边
                path.push(v);//当v的度数为0时，将v入栈
                stack.pop();//回溯时，从搜索路径中弹出v
            } else {
                for (int w : G.adj(v)) {
                    G.removeEdge(v, w);//“标记”已经访问的边
                    stack.push(w);//将与v邻接的w入搜索栈，表示可以沿着 v 到 w 的边继续遍历
                    break;
                }
            }
        }
        ArrayList<Integer> eulerPath = new ArrayList<>();
        while (!path.isEmpty()) {
            eulerPath.add(path.pop());//将路径正向
        }
        return eulerPath;
    }

    public static void main(String[] args) {
        var s = new Scanner(System.in);
        System.out.println("输入顶点个数：");
        int V = s.nextInt();
        int maxE = (V - 1) * V / 2;
        System.out.println("请输入边的个数（小于等于" + maxE + "）：");
        int E = s.nextInt();
//        System.out.println("V: " + V + " E: " + E);
        if (E > maxE) {
            System.out.println("边数不符合要求!");
            return;
        }
        var e = new Experiment4(V, E);
        System.out.println("欧拉路：" + (e.res.isEmpty() ? "不存在" : ""));
        for (var i = 0; i < e.res.size(); i++) {
            if (i == 0)
                System.out.print(e.res.get(i));
            else
                System.out.print("->" + e.res.get(i));
        }
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    private class Graph {
        private final int V; //顶点数目
        private final List<Integer>[] adjacency; //邻接表
        private int E;

        public Graph(int V) {
            this.V = V;
            this.E = 0;
            //noinspection unchecked
            adjacency = (List<Integer>[]) new ArrayList[V]; // 初始化邻接表
            for (int i = 0; i < V; i++) {
                this.adjacency[i] = new ArrayList<>();//为每一个顶点的列表初始化
            }
        }

        public void addEdge(int v, int w) {
            adjacency[v].add(w);
            adjacency[w].add(v);
            E++;
        }

        //返回一个顶点的所有邻接的顶点
        public Iterable<Integer> adj(int v) {
            return adjacency[v];
        }

        public int degree(int v) {
            int degree = 0;
            for (int ignored : adjacency[v]) {
                degree++;
            }
            return degree;
        }

        public void removeEdge(int v, int w) {
            adjacency[v].remove(Integer.valueOf(w));
            adjacency[w].remove(Integer.valueOf(v));
            E--;
        }

        public String toString() {
            StringBuilder s = new StringBuilder(V + " vertices, " + E + " edges\n");
            for (int v = 0; v < V; v++) {
                s.append(v).append(": ");
                for (int w : this.adj(v)) {
                    s.append(w).append(" ");
                }
                s.append("\n");
            }
            return s.toString();
        }
    }
}
