package org.example.Graph;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/15
 */
public class MyDFS {
    private final boolean[] marked;
    private int count;

    public MyDFS(MyGraph G, int s) {
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    private void dfs(MyGraph G, int v) {
        marked[v] = true;
        count++;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
    }

    public boolean marked(int v) {
        return marked[v];//v是否于s联通
    }

    public int count() {
        return count;//搜索图的过程中经过的顶点数，只有count等于图的顶点数目时，图才是联通的
    }
}
