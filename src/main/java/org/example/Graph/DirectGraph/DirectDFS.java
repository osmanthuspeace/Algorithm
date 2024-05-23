package org.example.Graph.DirectGraph;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/19
 */
public class DirectDFS {
    private final boolean[] marked;

    //找到从s可达的所有顶点，这些顶点都会被标记为true
    public DirectDFS(DirectGraph G, int s) {
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    //找到从sources中的所有顶点可达的所有顶点
    public DirectDFS(DirectGraph G, Iterable<Integer> sources) {
        marked = new boolean[G.V()];
        for (int v : sources) {
            if (!marked[v]) {
                dfs(G, v);
            }
        }
    }

    private void dfs(DirectGraph G, int cur) {
        marked[cur] = true;
        for (int v : G.adj(cur)) {
            if (!marked[v]) {
                dfs(G, v);
            }
        }
    }

    public boolean marked(int v) {
        return marked[v];
    }
}
