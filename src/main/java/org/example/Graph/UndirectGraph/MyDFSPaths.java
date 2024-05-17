package org.example.Graph.UndirectGraph;

import java.util.Stack;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/15
 */
public class MyDFSPaths {
    private final boolean[] marked;//检查这个顶点是否已经被搜索过
    private final int[] edgeTo;//从起点到某个顶点的已知路径上的最近的一个顶点
    private final int s;//起点

    public MyDFSPaths(MyGraph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        dfs(G, s);//以s为起点进行搜索
    }

    private void dfs(MyGraph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        var path = new Stack<Integer>();
        for (int x = v; x != s; x = edgeTo[x]) {
            path.push(x);
        }
        path.push(s);
        return path;
    }

    public boolean marked(int v) {
        return marked[v];//v是否与s联通
    }

}
