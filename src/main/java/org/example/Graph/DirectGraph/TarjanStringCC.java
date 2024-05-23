package org.example.Graph.DirectGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/22
 */
@SuppressWarnings("unchecked")
public class TarjanStringCC {

    private int[] dfs;//;记录每一个点的搜索次序
    private int[] low;//记录当前路径上的所有点中dfs的最小值，即最远（最早）能联通到哪一个祖先节点（在搜索时，搜到的一个环可能只是某个强联通分量的一部分）
    private boolean[] onStack;
    private Stack<Integer> stack;//储存当前的强联通分量
    private int id;
    private List<List<Integer>> sccs;//表示所有强联通分量
    private List<Integer>[] graph;//邻接表数组表示图

    public static void main(String[] args) {
        List<Integer>[] graph = new List[8];
        for (int i = 0; i < 8; i++) {
            graph[i] = new ArrayList<>();
        }

        graph[0].add(1);
        graph[1].add(2);
        graph[2].add(0);
        graph[2].add(3);
        graph[3].add(4);
        graph[4].add(5);
        graph[5].add(6);
        graph[6].add(4);
        graph[6].add(7);

        TarjanStringCC tarjan = new TarjanStringCC();
        List<List<Integer>> sccs = tarjan.findSCCs(graph);

        for (List<Integer> scc : sccs) {
            System.out.println("SCC: " + scc);
        }
    }

    public List<List<Integer>> findSCCs(List<Integer>[] graph) {
        int V = graph.length;
        dfs = new int[V];
        low = new int[V];
        onStack = new boolean[V];
        stack = new Stack<>();
        sccs = new ArrayList<>();
        this.graph = graph;

        for (int i = 0; i < V; i++) {
            dfs[i] = -1;
        }

        for (int v = 0; v < V; v++) {
            if (dfs[v] == -1) {
                dfs(v);
            }
        }
        return sccs;
    }

    private void dfs(int cur) {
        stack.push(cur);
        onStack[cur] = true;
        dfs[cur] = low[cur] = id;//如果cur的子节点中没有一个和cur的祖先联通，那么dfs[cur]就一定是cur以及cur的子节点中dfs值最小的
        id++;
        for (int adjoin : graph[cur]) {
            if (dfs[adjoin] == -1) {//如果邻接的节点未被访问
                dfs(adjoin);
                low[cur] = Math.min(low[cur], low[adjoin]);//dfs()调用完成之后，cur的子节点adjoin才被更新，所以此时才能更新cur。由于id在每一次调用都++的缘故，cur的子节点的low一定大于cur的low，此时如果没有环，那么cur就不会被更新
            } else if (onStack[adjoin]) {//如果邻接的节点被访问过了，而且是当前搜索路径的一部分（说明有环）
                low[cur] = Math.min(low[cur], low[adjoin]);
            }
        }
        //这个条件意味着上面这个for循环没有改变low的值
        //有可能是因为这个点没有出度（一个点单独构成一个分量）
        //或者就是意味着：这些节点中的最早被访问的节点（即DFS编号最小的节点）就是cur本身，而这些节点，就构成了一个强联通分量
        if (dfs[cur] == low[cur]) {
            List<Integer> scc = new ArrayList<>();
            while (true) {
                int node = stack.pop();
                onStack[node] = false;
                scc.add(node);
//                low[node] = dfs[cur];
                if (node == cur) break;
            }
            sccs.add(scc);
        }
    }

    //图的缩点操作：将一个强联通分量看作一个节点，只保留那些不在强连通分量里的边，结果一定是DAG
    public List<Integer>[] buildContractedGraph(List<Integer>[] graph, List<List<Integer>> sccs) {
        int V = graph.length;
        List<Integer>[] contractedGraph = new List[sccs.size()];
        for (int i = 0; i < sccs.size(); i++) {
            contractedGraph[i] = new ArrayList<>();
        }
        int[] nodeToScc = new int[V];
        for (int i = 0; i < sccs.size(); i++) {
            var scc = sccs.get(i);
            for (int node : scc) {//对每个强连通分量scc，遍历其中的每个节点 node
                nodeToScc[node] = i;//把同一个分量中的节点染成一个颜色
            }
        }
        for (int v = 0; v < V; v++) {
            for (int w : graph[v]) {//v->w
                int wScc = nodeToScc[w];
                int vScc = nodeToScc[v];
                if (wScc != vScc) {
                    contractedGraph[vScc].add(wScc);
                }
            }
        }
        return contractedGraph;
    }
}
