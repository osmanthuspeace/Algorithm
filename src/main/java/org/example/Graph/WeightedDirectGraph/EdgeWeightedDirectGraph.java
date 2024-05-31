package org.example.Graph.WeightedDirectGraph;

import edu.princeton.cs.algs4.EdgeWeightedDigraph;

import java.util.ArrayList;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/29
 */
public class EdgeWeightedDirectGraph extends EdgeWeightedDigraph {

    private final int V;
    private final ArrayList<DiEdge>[] adj;
    private int E;

    public EdgeWeightedDirectGraph(int V) {
        super(V);
        this.V = V;
        this.E = 0;
        adj = (ArrayList<DiEdge>[]) new ArrayList[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new ArrayList<>();
        }

    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(DiEdge e) {
        adj[e.from()].add(e);
        E++;
    }

    //从v指出的边
    public Iterable<DiEdge> Adj(int v) {
        return adj[v];
    }

    //该有向图的所有边
    public Iterable<DiEdge> Edges() {
        var res = new ArrayList<DiEdge>();
        for (int v = 0; v < V; v++) {
            res.addAll(adj[v]);//将从v指出的边的集合全部加入res集合中
        }
        return res;
    }

}
