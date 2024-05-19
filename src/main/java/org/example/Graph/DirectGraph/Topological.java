package org.example.Graph.DirectGraph;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/19
 */
public class Topological {
    private Iterable<Integer> order;//顶点的拓扑顺序

    public Topological(DirectGraph G) {
        var cycleFinder = new DirectCycle(G);
        if (!cycleFinder.hasCycle()) {
            var dfs = new DFOrder(G);
            order = dfs.reversePost();
        }
    }

    public Iterable<Integer> order() {
        return order;
    }

    public boolean isDAG() {//是否是有向无环图(Directed Acyclic Graph)
        return order != null;
    }
}
