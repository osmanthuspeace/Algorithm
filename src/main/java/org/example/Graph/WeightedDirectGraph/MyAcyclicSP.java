package org.example.Graph.WeightedDirectGraph;

import edu.princeton.cs.algs4.Topological;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/30
 */

//如果某个加权有向图是无环的，可以按照拓扑序松弛顶点，这样时间复杂度就是O(E+V)
public class MyAcyclicSP {
    private final DiEdge[] edgeTo;
    private final double[] distTo;

    public MyAcyclicSP(EdgeWeightedDirectGraph G, int s) {
        distTo = new double[G.V()];
        edgeTo = new DiEdge[G.V()];
        for (int i = 0; i < G.V(); i++) {
            distTo[i] = Double.NEGATIVE_INFINITY;//改成负无穷以计算最长路径
        }
        distTo[s] = 0.0;
        var top = new Topological(G);//此处需要一个正确的加权有向无环图的拓扑序，使用自己的数据结构就不能用edu.princeton.cs.alg4给的拓扑
        for (int v : top.order()) {
            relax(G, v);    //顶点的松弛
        }

    }

    private void relax(DiEdge e) {
        int v = e.from();
        int w = e.to();
        if (distTo[w] < distTo[v] + e.weight()) {//改成小于号以计算最长路径
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
        }
    }

    private void relax(EdgeWeightedDirectGraph G, int v) {
        for (var e : G.Adj(v)) {
            relax(e);
        }
    }

    public double distTo(int v) {
        return distTo[v];
    }
}
