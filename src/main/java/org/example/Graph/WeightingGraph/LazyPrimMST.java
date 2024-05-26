package org.example.Graph.WeightingGraph;

import java.util.ArrayDeque;
import java.util.PriorityQueue;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/26
 */
//Prim算法的延时实现
public class LazyPrimMST {

    //由切分定理，当两个切分的最小生成树已经求出，则只要在横切边中找到权重最小的边加入最小生成树即可
    private final boolean[] marked;
    private final ArrayDeque<MyEdge> mst;//存放最小生成树的边
    private final PriorityQueue<MyEdge> transverses;//用来存放横切边（默认实现是小根堆）

    public LazyPrimMST(MyEdgeWeightedGraph G) {
        transverses = new PriorityQueue<>();
        marked = new boolean[G.V()];
        mst = new ArrayDeque<>();
        visit(G, 0);//先将与第一个节点的相关的边加入优先队列
        while (!transverses.isEmpty()) {
            var e = transverses.remove();//从队列中得到权重最小的边
            int v = e.oneNode();
            int w = e.otherNode(v);//获取当前边的两端的节点
            if (marked[v] && marked[w]) {
                continue;//当一条边的两端都被访问过，该边失效，跳过
            }
            mst.add(e);//将权重最小的边加入最小生成树
            if (!marked[v]) {
                visit(G, v);
            }
            if (!marked[w]) {
                visit(G, w);
            }
        }
    }

    private void visit(MyEdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (var e : G.adj(v)) {
            if (!marked[e.otherNode(v)]) {
                transverses.add(e);
            }
        }
    }

    public Iterable<MyEdge> edges() {
        return mst;
    }
}
