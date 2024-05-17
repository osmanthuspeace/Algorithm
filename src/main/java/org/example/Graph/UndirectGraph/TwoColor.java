package org.example.Graph.UndirectGraph;

import edu.princeton.cs.algs4.Graph;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/17
 */

//判断图是否为二分图
//即是否可以做到用两种颜色将图的所有顶点着色，使得任意一条边的两个端点颜色不同
public class TwoColor {
    private final boolean[] marked;
    private final boolean[] color;
    private boolean isTwoColorable = true;

    public TwoColor(Graph G) {
        marked = new boolean[G.V()];
        color = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                dfs(G, v);
            }
        }
    }

    private void dfs(Graph G, int cur) {
        marked[cur] = true;
        for (int adjoin : G.adj(cur)) {
            if (!marked[cur]) {
                color[adjoin] = !color[cur];
                dfs(G, adjoin);
            } else if (color[adjoin] == color[cur]) {
                isTwoColorable = false;
            }
        }
    }

    public boolean isBipartite() {
        return isTwoColorable;
    }
}
