package org.example.Graph.UndirectGraph;

import edu.princeton.cs.algs4.Graph;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/17
 */

//搜索联通分量
public class ConnectedComponent {

    private boolean[] marked;
    private int[] id;
    private int count;

    public ConnectedComponent(Graph G) {
        this.count = 0;
        marked = new boolean[G.V()];
        id = new int[G.V()];
        for (int s = 0; s < G.V(); s++) {//遍历每一个顶点，寻找可能的联通分量的起点
            if (!marked[s]) {
                dfs(G, s);
                count++;
            }
        }
    }

    private void dfs(Graph G, int v) {
        marked[v] = true;
        id[v] = count;
        for (int w : G.adj(v)) {
            dfs(G, w);
        }
    }

    public boolean isConnected(int v, int w) {
        return id[v] == id[w];
    }

    public int count() {
        return count;
    }

    //返回节点v所在的联通分量的标识符（0～count-1）
    public int id(int v) {
        return id[v];
    }
}
