package org.example.Graph.UndirectGraph;

import edu.princeton.cs.algs4.Graph;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/17
 */
public class Cycle {
    //当不存在子环和平行边的时候，判断图中有无环
    private final boolean[] marked;
    private boolean hasCycle;

    public Cycle(Graph G) {
        marked = new boolean[G.V()];
        for (int i = 0; i < G.V(); i++) {
            if (!marked[i]) {
                dfs(G, i, i);
            }
        }
    }

    private void dfs(Graph G, int cur, int parent) {
        marked[cur] = true;
        for (int adjoin : G.adj(cur)) {
            if (!marked[adjoin]) {
                dfs(G, adjoin, cur);
            } else if (adjoin != parent) {
                //当一个节点的邻接节点被再次访问，且该邻接节点不是当前节点的父节点，说明有环
                //即：如果有一个节点将要被重复访问，那就有环，但是要排除从前一个节点退回的情况
                hasCycle = true;
            }
        }
    }

    public boolean hasCycle() {
        return hasCycle;
    }
}
