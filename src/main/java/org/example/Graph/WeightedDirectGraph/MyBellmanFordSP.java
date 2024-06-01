package org.example.Graph.WeightedDirectGraph;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/31
 */
public class MyBellmanFordSP {

    private final double[] distTo;
    private final DiEdge[] edgeTo;
    private final boolean[] InQ;//表示顶点是否在队列中
    private final Queue<Integer> queue;//正在被放松的顶点
    private int cost;//表示relax()的调用次数
    private Iterable<DiEdge> cycle;//表示edgeTo[]中是否有负权重环

    public MyBellmanFordSP(EdgeWeightedDirectGraph G, int start) {
        distTo = new double[G.V()];
        edgeTo = new DiEdge[G.V()];
        InQ = new boolean[G.V()];
        queue = new ArrayDeque<>();
        for (int i = 0; i < G.V(); i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }
        distTo[start] = 0.0;
        queue.offer(start);
        InQ[start] = true;
        while (!queue.isEmpty() && !hasNegativeCycle()) {
            int v = queue.remove();//取出最早入队的元素
            InQ[v] = false;
            relax(G, v);
        }
    }

    private void relax(EdgeWeightedDirectGraph G, int v) {
        for (var e : G.Adj(v)) {
            int w = e.to();
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                if (!InQ[w]) {
                    queue.offer(w);
                    InQ[w] = true;
                }
            }
            //每调用V次relax()方法后检查负权重环
            if (cost++ % G.V() == 0) {
                //在最短路径无负权回路的情况下，V−1 次松弛后所有最短路径已经确定（对于一个顶点数为n的图，从一点到另一点的最短路径长度不会大于n-1）
                //如果还能松弛，则说明存在负权回路
                findNegativeCycle();
            }
        }
    }

    public double distTo(int v) {
        return distTo[v];
    }

    private void findNegativeCycle() {
        int V = edgeTo.length;
        var spt = new EdgeWeightedDirectGraph(V);//最短路径树
        for (int v = 0; v < V; v++) {
            if (edgeTo[v] != null) {
                spt.addEdge(edgeTo[v]);
            }
        }
        var cf = new EdgeWeightedDiCycle(spt);
        cycle = cf.cycle();
        if (cycle != null) {
            System.out.println("Negative cycle found: ");
            for (DiEdge e : cycle) {
                System.out.print(e + "\n");
            }
            System.out.println();
        }
    }

    public boolean hasNegativeCycle() {
        return cycle != null;
    }

    public Iterable<DiEdge> negativeCycle() {
        return cycle;
    }

    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<DiEdge> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        if (hasNegativeCycle()) {
            // 如果存在负权重环，直接返回null
            return null;
        }
        var path = new Stack<DiEdge>();
        for (var e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {//回溯最短路径
            path.push(e);
        }
        var res = new ArrayDeque<DiEdge>();
        while (!path.isEmpty()) {
            res.offer(path.pop());
        }
        return res;
    }

    public static void main(String[] args) {

        int V = StdIn.readInt();
        int n = StdIn.readInt();
        EdgeWeightedDirectGraph G = new EdgeWeightedDirectGraph(V);
        for (int i = 0; i < n; i++) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            double weight = StdIn.readDouble();
            G.addEdge(new DiEdge(v, w, weight));
        }

        int startVertex = 0;
        MyBellmanFordSP sp = new MyBellmanFordSP(G, startVertex);

        if (sp.hasNegativeCycle()) {
            return;
        }
        // 打印从起点到所有顶点的最短路径
        for (int v = 0; v < G.V(); v++) {
            if (sp.hasPathTo(v)) {
                StdOut.printf("%d to %d (%.2f)  ", startVertex, v, sp.distTo(v));
                for (DiEdge e : sp.pathTo(v)) {
                    StdOut.print(e + "   ");
                }
                StdOut.println();
            } else {
                StdOut.printf("%d to %d no path\n", startVertex, v);
            }
        }
    }
}
