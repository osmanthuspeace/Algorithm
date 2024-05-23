package org.example.Graph.DirectGraph;

import java.util.ArrayList;
import java.util.List;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/23
 */
public class TarjanBridge {
    //在无向图中才有割边和割点
    /*
    判定：
        在一张无向图中，判断边u-v是否为桥（割边），需要满足如下条件：dfs[u] < low[v]
    理解：
        如果u和v在一个联通分量中，那么这个分量中的low一定<=所有的dfs，现在dfs[u] < low[v]，只能说明不在一个分量中
        虽然是无向图，但是由于cur==parent时会被跳过，所以在某种程度上，搜索时一个桥的两边时单向的
     */
    /*
    判定：
        如果根节点有两个或以上子树，那么根节点就是割点
        对于非根节点u，如果存在某个子节点v，使得low[v] >= ids[u]，那么节点u就是割点
    理解：
        当顶点v的所有子顶点可以不通过v而访问到v的祖先顶点（有环），那么说明此时去掉v不影响图的连通性，v就不是割点
        相反，如果顶点v有一个子顶点，必须通过v才能访问到v的祖先顶点，那么去掉v后，顶点v的祖先顶点和子顶点就不连通了，说明v是一个割点
     */
    private int[] dfs; // 记录每一个点的搜索次序，在访问一个顶点后，它的dfn的值就确定下来了，不会再改变
    private int[] low; // 记录当前路径上的所有点中dfs的最小值
    private int id;
    private List<Integer>[] graph; // 邻接表数组表示图
    private List<List<Integer>> bridges; // 储存所有的桥
    @SuppressWarnings("MismatchedReadAndWriteOfArray")
    private boolean[] isArticulationPoint;//储存割点

    public List<List<Integer>> findBridges(List<Integer>[] graph) {
        int V = graph.length;
        dfs = new int[V];
        low = new int[V];
        bridges = new ArrayList<>();
        isArticulationPoint = new boolean[V];
        this.graph = graph;

        for (int i = 0; i < V; i++) {
            dfs[i] = -1;
        }

        for (int v = 0; v < V; v++) {
            if (dfs[v] == -1) {
                dfs(v, -1); // 开始DFS，-1表示没有父节点
            }
        }
        return bridges;
    }

    private void dfs(int cur, int parent) {
        dfs[cur] = low[cur] = id++;
        int children = 0; // 子节点计数
        for (int adjoin : graph[cur]) {
            if (adjoin == parent) continue;//新low值时排除父顶点，和求无向图的环的思路一样，不需要stack保存路径，为了避免无向图中一条边被计算两次，直接排除父节点就行

            if (dfs[adjoin] == -1) { // 如果邻接的节点未被访问
                dfs(adjoin, cur);
                low[cur] = Math.min(low[cur], low[adjoin]);
                children++;
                // 判断是否为桥
                if (dfs[cur] < low[adjoin]) {
                    bridges.add(List.of(cur, adjoin));
                }
                // 如果不是根节点，且满足割点条件
                if (parent != -1 && low[adjoin] >= dfs[cur]) {
                    isArticulationPoint[cur] = true;
                }
            } else {
                low[cur] = Math.min(low[cur], dfs[adjoin]);
            }
        }
        // 如果是根节点，且有两个或更多子节点
        if (parent == -1 && children > 1) {
            isArticulationPoint[cur] = true;
        }
    }
}
