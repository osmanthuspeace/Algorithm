package org.example.Solutions;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/19
 */
@SuppressWarnings("unchecked")
public class Solution2 {
    private int id;
    private TreeNode lowestCommonAncestorResult;
    private boolean[] marked;
    @SuppressWarnings({"FieldCanBeLocal", "MismatchedQueryAndUpdateOfCollection"})
    private ArrayDeque<Edge> mst;//存放最小生成树的边
    private PriorityQueue<Edge> transverses;

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    //1192. 查找集群内的关键连接
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        int[] dfs = new int[n];
        int[] low = new int[n];
        this.id = 0;
        List<Integer>[] graph = new List[n];
        List<List<Integer>> bridges = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
            dfs[i] = -1;
        }

        for (var conn : connections) {
            graph[conn.get(0)].add(conn.get(1));
            graph[conn.get(1)].add(conn.get(0));
        }
        for (int i = 0; i < n; i++) {
            if (dfs[i] == -1) {//等于-1时是没有访问过
                dfs(i, -1, dfs, low, graph, bridges);
            }
        }
        return bridges;
    }

    private void dfs(int cur, int parent, int[] dfs, int[] low, List<Integer>[] graph, List<List<Integer>> bridges) {
        dfs[cur] = low[cur] = id++;
        for (int adjoin : graph[cur]) {
            if (adjoin == parent) continue;
            if (dfs[adjoin] == -1) {
                dfs(adjoin, cur, dfs, low, graph, bridges);
                low[cur] = Math.min(low[cur], low[adjoin]);
                if (dfs[cur] < low[adjoin]) {
                    bridges.add(List.of(cur, adjoin));
                }
            } else {
                low[cur] = Math.min(low[cur], dfs[adjoin]);
            }
        }
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (p.val > q.val) {
            dfs(root, q, p);
            return lowestCommonAncestorResult;
        }
        dfs(root, p, q);
        return lowestCommonAncestorResult;
    }

    private void dfs(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null)
            return;
        if (p.val <= root.val && q.val >= root.val) {
            lowestCommonAncestorResult = root;
        } else if (p.val > root.val) {
            dfs(root.right, p, q);
        } else { // q.val < root.val
            dfs(root.left, p, q);
        }
    }

    private static class Edge implements Comparable<Edge> {
        private final int v;
        private final int w;
        private final int weight;

        public Edge(int v, int w, int weight) {
            this.v = v;
            this.w = w;
            this.weight = weight;
        }

        //返回边的顶点之一
        public int oneNode() {
            return v;
        }

        //返回边的另一个顶点
        public int otherNode(int vertex) {
            if (vertex == v) return w;
            else if (vertex == w) return v;
            else return vertex;
        }

        public double weight() {
            return weight;
        }

        @Override
        public int compareTo(Edge that) {
            return Double.compare(this.weight(), that.weight());
        }

    }

    @SuppressWarnings("unchecked")
    private static class EdgeWeightedGraph {
        private final int V;
        private final List<Edge>[] adj;

        public EdgeWeightedGraph(int V) {
            this.V = V;
            this.adj = new List[V];
            for (int i = 0; i < V; i++) {
                adj[i] = new ArrayList<>();
            }
        }

        public int V() {
            return this.V;
        }

        public void addEdge(Edge e) {
            int v = e.oneNode();
            int w = e.otherNode(v);
            adj[v].add(e);
            adj[w].add(e);
        }

        public Iterable<Edge> adj(int v) {
            return adj[v];
        }
    }

    public int minCostConnectPoints(int[][] points) {
        var G = new EdgeWeightedGraph(points.length);
        int result = 0;
        transverses = new PriorityQueue<>();
        marked = new boolean[G.V()];
        mst = new ArrayDeque<>();
        for (int i = 0; i < points.length; i++) {
            for (int j = i; j < points.length; j++) {
                int distance = Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
                var e = new Edge(i, j, distance);
                G.addEdge(e);
            }
        }
        visit(G, 0);
        while (!transverses.isEmpty()) {
            var e = transverses.remove();
            int v = e.oneNode();
            int w = e.otherNode(v);
            if (marked[v] && marked[w]) {
                continue;
            }
            mst.add(e);
            result += e.weight;
            if (!marked[v]) {
                visit(G, v);
            }
            if (!marked[w]) {
                visit(G, w);
            }
        }
        return result;
    }

    private void visit(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (var e : G.adj(v)) {
            if (!marked[e.otherNode(v)]) {
                transverses.add(e);
            }
        }
    }

}
