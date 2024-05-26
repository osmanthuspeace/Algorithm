package org.example.Graph.WeightingGraph;

import org.example.Solutions.Solution;

import java.util.ArrayDeque;
import java.util.PriorityQueue;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/26
 */
public class KruskalMST {
    private final ArrayDeque<MyEdge> mst;

    public KruskalMST(MyEdgeWeightedGraph G) {
        mst = new ArrayDeque<>();
        var edges = new PriorityQueue<MyEdge>();
        for (var e : G.edges()) {
            edges.add(e);
        }
        Solution.UnionFind uf = new Solution.UnionFind(G.V());

        while (!edges.isEmpty() && mst.size() < G.V() - 1) {
            var e = edges.remove();//取出权重最小的边
            int v = e.oneNode();
            int w = e.otherNode(v);
            if (uf.find(v) == uf.find(w)) {//忽略失效的边
                continue;
            }
            uf.union(v, w);//用并查集代替marked标记
            mst.add(e);
        }
    }

    public Iterable<MyEdge> edges() {
        return mst;
    }
}
