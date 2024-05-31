package org.example.Graph.WeightedDirectGraph;

import java.util.Stack;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/30
 */
public class MySP {

    private final double[] distTo;//distTo[v]表示从起点到v的最短路径长度
    private final DiEdge[] edgeTo;//edgeTo[v]表示从起点到v的最短路径的最后一条边

    public MySP(EdgeWeightedDirectGraph G, int s) {
        distTo = new double[G.V()];
        edgeTo = new DiEdge[G.V()];
        for (int i = 0; i < G.V(); i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0.0;
    }

    //边的松弛操作
    //本来从起点到v，w顶点的最短路径是两条：s-v和s-w
    //又已知v和w是相邻的
    //如果s-v-w这样走比直接s-w更短，就更新s-w的最短路
    private void relax(DiEdge e) {
        int v = e.from();
        int w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
        }
    }

    //顶点的松弛
    //放松给定顶点的所有边
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
