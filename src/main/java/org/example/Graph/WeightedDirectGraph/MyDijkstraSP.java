package org.example.Graph.WeightedDirectGraph;

import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayDeque;
import java.util.Stack;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/30
 */
//一种即时的贪心算法，适用于权重非负的加权有向图的单起点最短路径问题，时间复杂度为O(ElogV)
    //如果不加顶点的判断，允许负权边的存在，算法会退化到指数级别（2^n）
//Dijkstra默认一个节点对另一个节点的单向边是肯定增加/不变权重的，这就是为什么算法会对已经找过邻居的节点，不再重复寻找
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

            //即使从队列中删除，仍然会更新这个顶点，所以似乎是可以处理负权重边的？
            //但是负权重环肯定无法处理
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
        var res = new ArrayDeque<DiEdge>();
        while (!path.isEmpty()) {
            res.offer(path.pop());
        }
        return res;
    }

    public static void main(String[] args) {
        // 构建一个加权有向图
        EdgeWeightedDirectGraph G = new EdgeWeightedDirectGraph(4);

        //https://stackoverflow.com/questions/6799172/negative-weights-using-dijkstras-algorithm
        G.addEdge(new DiEdge(0, 1, 1));
        G.addEdge(new DiEdge(0, 2, 0));
        G.addEdge(new DiEdge(0, 3, 99));
        G.addEdge(new DiEdge(1, 2, 1));
        G.addEdge(new DiEdge(3, 1, -300));

        int startVertex = 0;

        // 使用Dijkstra算法计算最短路径
        MyDijkstraSP sp = new MyDijkstraSP(G, startVertex);

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
/*

如果要使用普通的优先队列，需要使用一个辅助类记录额外的信息：

// 辅助类，用于优先队列中的元素表示，其中的vertex就是索引
private static class VertexDist {
    int vertex;
    double dist;

    VertexDist(int vertex, double dist) {
        this.vertex = vertex;
        this.dist = dist;
    }
}
//初始化时指定比较器：
pq = new PriorityQueue<>(Comparator.comparingDouble(v -> v.dist));

 */
