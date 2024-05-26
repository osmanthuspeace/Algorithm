package org.example.Graph.UndirectGraph;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Queue;

import java.util.Stack;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/17
 */
public class MyBFS {

    private final boolean[] marked;//表示是否已知到达某个顶点的最短路
    private final int[] edgeTo;
    private final int start;

    public MyBFS(Graph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.start = s;
        bfs(G, s);
    }

    private void bfs(Graph G, int s) {
        var q = new Queue<Integer>();
        marked[s] = true;
        q.enqueue(s);
        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    q.enqueue(w);
                    marked[w] = true;
                    edgeTo[w] = v;//保存到顶点w的最短路径的最后一条边为v-w
                }
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        var path = new Stack<Integer>();
        for (int x = v; x != start; x = edgeTo[x]) {
            path.push(x);
        }
        path.push(start);
        return path;
    }
}
