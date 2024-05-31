package org.example.Graph.WeightedDirectGraph;

import edu.princeton.cs.algs4.IndexMinPQ;

import java.util.Stack;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/30
 */
//一种即时的贪心算法，适用于权重非负的加权有向图的单起点最短路径问题，时间复杂度为O(ElogV)
public class MyDijkstraSP {
    private final double[] distTo;//distTo[v]表示从起点到v的最短路径长度
    private final DiEdge[] edgeTo;//edgeTo[v]表示从起点到v的最短路径的最后一条边
    private final IndexMinPQ<Double> pq;//存放最短路径树可以通过一条边直接到达的顶点的权重，索引是顶点的值

    public MyDijkstraSP(EdgeWeightedDirectGraph G, int s) {
        distTo = new double[G.V()];
        edgeTo = new DiEdge[G.V()];
        pq = new IndexMinPQ<>(G.V());
        for (int i = 0; i < G.V(); i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0.0;
        pq.insert(s, 0.0);
        while (!pq.isEmpty()) {
            relax(G, pq.delMin());//每次选择距离起点最近的非树顶点
        }
    }

    //边的松弛操作
    private void relax(DiEdge e) {
        int v = e.from();
        int w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            if (pq.contains(w)) pq.changeKey(w, distTo[w]);
            else pq.insert(w, distTo[w]);
        }
    }

    //顶点的松弛
    private void relax(EdgeWeightedDirectGraph G, int v) {
        for (var e : G.Adj(v)) {
            relax(e);
        }
    }

    //返回从起点到v的最短距离，不存在则为无穷大
    public double distTo(int v) {
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<DiEdge> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        var path = new Stack<DiEdge>();
        for (var e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {//回溯最短路径
            path.push(e);
        }
        return path;
    }
}
