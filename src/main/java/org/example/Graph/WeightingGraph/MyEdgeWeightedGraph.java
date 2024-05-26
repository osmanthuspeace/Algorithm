package org.example.Graph.WeightingGraph;

import java.util.ArrayList;
import java.util.List;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/26
 */

//处理加权无向图
@SuppressWarnings("unchecked")
public class MyEdgeWeightedGraph {

    private final int V;
    private int E;
    private final List<MyEdge>[] adj;

    public MyEdgeWeightedGraph(int V) {
        this.V = V;
        this.E = 0;
        this.adj = new List[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new ArrayList<>();
        }
    }

    public int V() {
        return this.V;
    }

    public int E() {
        return this.E;
    }

    public void addEdge(MyEdge e) {
        int v = e.oneNode();
        int w = e.otherNode(v);
        adj[v].add(e);//虽然两个点都会储存这个条边，但是变得引用只有一个
        adj[w].add(e);
        E++;
    }

    public Iterable<MyEdge> adj(int v) {
        return adj[v];
    }

    public Iterable<MyEdge> edges() {
        var b = new ArrayList<MyEdge>();
        for (int v = 0; v < V; v++) {
            for (MyEdge e : adj[v]) {
                if (e.otherNode(v) > v)//一条边会被储存两次，u-v和v-u，只取u-v（u<v）
                    b.add(e);
            }
        }
        return b;
    }
}
