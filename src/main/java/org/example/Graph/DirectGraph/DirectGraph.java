package org.example.Graph.DirectGraph;

import java.util.ArrayList;
import java.util.List;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/19
 */
@SuppressWarnings("unchecked")
public class DirectGraph {
    private final int V; //顶点数目
    private final List<Integer>[] adjacency; //邻接表
    private int E;

    public DirectGraph(int V) {
        this.V = V;
        this.E = 0;
        adjacency = (List<Integer>[]) new ArrayList[V]; // 初始化邻接表
        for (int i = 0; i < V; i++) {
            this.adjacency[i] = new ArrayList<>();//为每一个顶点的列表初始化
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    //有向图
    public void addEdge(int v, int w) {
        adjacency[v].add(w);
        E++;
    }

    public Iterable<Integer> adj(int v) {
        return adjacency[v];
    }

    public DirectGraph reverse() {
        var dg = new DirectGraph(V);
        for (int v = 0; v < V; v++) {
            for (int w : adj(v)) {
                dg.addEdge(w, v);
            }
        }
        return dg;
    }
}
