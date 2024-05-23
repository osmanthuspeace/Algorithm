package org.example.Graph.DirectGraph;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/19
 */
public class DFOrder {
    private final boolean[] marked;
    private final Queue<Integer> pre;//所有顶点的前序排列，即dfs()调用的顺序
    private final Queue<Integer> post;//所有顶点的后序排列，即dfs()调用完成（顶点遍历完成）的顺序
    private final Stack<Integer> reversePost;//逆后序排列（拓扑序）

    public DFOrder(DirectGraph G) {
        marked = new boolean[G.V()];
        pre = new ArrayDeque<>();
        post = new ArrayDeque<>();
        reversePost = new Stack<>();
        for (int v = 0; v < G.V(); v++) {
            dfs(G, v);
        }
    }

    private void dfs(DirectGraph G, int cur) {
        pre.add(cur);
        marked[cur] = true;
        for (int w : G.adj(cur)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
        post.add(cur);
        reversePost.push(cur);
    }

    public Iterable<Integer> pre() {
        return pre;
    }

    public Iterable<Integer> post() {
        return post;
    }

    public Iterable<Integer> reversePost() {
        return reversePost;
    }
}
