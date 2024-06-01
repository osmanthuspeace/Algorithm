package org.example.Graph.WeightedDirectGraph;

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.Stack;

/**
 * author: osmanthuspeace
 * createTime: 2024/6/1
 */
public class EdgeWeightedDiCycle {

    private final boolean[] marked;             // marked[v] = has vertex v been marked?
    private final DiEdge[] edgeTo;        // edgeTo[v] = previous edge on path to v
    private final boolean[] onStack;            // onStack[v] = is vertex on the stack?
    private Stack<DiEdge> cycle;    // directed cycle (or null if no such cycle)

    public EdgeWeightedDiCycle(EdgeWeightedDirectGraph G) {
        marked = new boolean[G.V()];
        onStack = new boolean[G.V()];
        edgeTo = new DiEdge[G.V()];
        for (int v = 0; v < G.V(); v++)
            if (!marked[v]) dfs(G, v);

    }

    private void dfs(EdgeWeightedDirectGraph G, int v) {
        onStack[v] = true;
        marked[v] = true;
        for (var e : G.Adj(v)) {
            int w = e.to();
            // short circuit if directed cycle found
            if (cycle != null) return;
            else if (!marked[w]) {
                edgeTo[w] = e;
                dfs(G, w);
            }

            // trace back directed cycle
            else if (onStack[w]) {
                cycle = new Stack<>();
                while (e.from() != w) {
                    cycle.push(e);
                    e = edgeTo[e.from()];
                }
                cycle.push(e);
                return;
            }
        }
        onStack[v] = false;
    }

    public Iterable<DiEdge> cycle() {
        return cycle;
    }

}
