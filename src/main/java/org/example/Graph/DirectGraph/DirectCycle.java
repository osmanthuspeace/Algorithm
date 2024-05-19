package org.example.Graph.DirectGraph;

import java.util.Stack;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/19
 */

//拓扑排序需要有向无环图，于是要检测这一特殊的情况
public class DirectCycle {
    private final boolean[] marked;//只记录节点是否被访问过，但是不能区分节点是被当前路径访问的，还是被其他路径访问的，只有在当前递归路径上的节点才会被认为可能构成环
    private final int[] edgeTo;
    private final boolean[] onStack;//(递归调用栈)上的所有顶点
    private Stack<Integer> cycle;//一个有向环中的所有顶点（如果存在）


    public DirectCycle(DirectGraph G) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        onStack = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                dfs(G, v);
            }
        }
    }

    private void dfs(DirectGraph G, int cur) {
        onStack[cur] = true;//调用开始时
        marked[cur] = true;
        for (int adjoin : G.adj(cur)) {
            if (hasCycle()) {//当有多个环的时候只能保存一个
                return;
            } else if (!marked[adjoin]) {
                edgeTo[adjoin] = cur;
                dfs(G, adjoin);
            } else if (onStack[adjoin]) {
                cycle = new Stack<>();
                for (int x = cur; x != adjoin; x = edgeTo[x]) {
                    cycle.push(x);
                }
                cycle.push(adjoin);
                cycle.push(cur);
            }
        }
        onStack[cur] = false;//调用结束时
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<Integer> cycle() {
        return cycle;
    }

    public static void main(String[] args) {
        DirectGraph g = new DirectGraph(2);
        g.addEdge(0, 1);
        DirectCycle c = new DirectCycle(g);
        System.out.println(c.cycle());
    }

}
