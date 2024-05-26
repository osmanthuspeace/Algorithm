package org.example.Solutions;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/16
 */
public class FindMazePaths {

    @SuppressWarnings("unchecked")
    private static class Graph {
        private final int V; //顶点数目
        private int E;
        private final List<Integer>[] adjacency; //邻接表
        private final boolean[] marked;
        @SuppressWarnings("MismatchedReadAndWriteOfArray")
        private final int[] edgeTo;
        private int pathCount; // 用于存储路径数量

        public Graph(int V) {
            this.V = V;
            this.E = 0;
            this.pathCount = 0;
            marked = new boolean[V + 1];
            edgeTo = new int[V + 1];
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

        private void dfs(Graph G, int cur, int end) {
            if (cur == end) {//递归终止条件：到达目标点
                pathCount++;
                return;
            }

            marked[cur] = true;
            for (int w : G.adj(cur)) {
                if (!marked[w]) {
                    edgeTo[w] = cur;//记录：到达w，要从cur来
                    dfs(G, w, end);
                    marked[w] = false;// 返回后取消标记，允许重新访问
                }
            }
            marked[cur] = false;
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

    public int init(int n, int m, int t, int sx, int sy, int fx, int fy) {
        int[][] maze = new int[n + 1][m + 1];
        int number = n * m;
        Graph g = new Graph(number + 1);
        Scanner s = new Scanner(System.in);
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                maze[i][j] = 0;
            }
        }
//        maze[sx][sy] = 1;
        for (int i = 1; i <= t; i++) {
            int tempi = s.nextInt();
            int tempj = s.nextInt();
            maze[tempi][tempj] = 1;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (maze[i][j] != 1) {
                    int index = m * (i - 1) + j;
                    //只向右下添加边，避免重复
//                    if (j > 1 && maze[i][j - 1] != 1) {
//                        g.addEdge(index, index - 1);//添加左边的节点
//                    }
                    if (j < m && maze[i][j + 1] != 1) {
                        g.addEdge(index, index + 1);//添加右边的节点
                    }
//                    if (i > 1 && maze[i - 1][j] != 1) {
//                        g.addEdge(index, index - m);//添加上面的节点
//                    }
                    if (i < n && maze[i + 1][j] != 1) {
                        g.addEdge(index, index + m);//添加下面的节点
                    }
                }
            }
        }
//        System.out.println(g);
        g.dfs(g, m * (sx - 1) + sy, m * (fx - 1) + fy);
        return g.pathCount;
    }

    public static void main(String[] args) {
        FindMazePaths f = new FindMazePaths();
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        int m = s.nextInt();
        int t = s.nextInt();
        int sx = s.nextInt();
        int sy = s.nextInt();
        int fx = s.nextInt();
        int fy = s.nextInt();
        int res = f.init(n, m, t, sx, sy, fx, fy);
        System.out.println(res);
    }
}
