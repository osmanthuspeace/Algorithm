package org.example.Solutions;

import java.util.ArrayList;
import java.util.List;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/19
 */
public class Solution2 {
    private int id;

    //1192. 查找集群内的关键连接
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        int[] dfs = new int[n];
        int[] low = new int[n];
        this.id = 0;
        List<Integer>[] graph = new List[n];
        List<List<Integer>> bridges = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
            dfs[i] = -1;
        }

        for (var conn : connections) {
            graph[conn.get(0)].add(conn.get(1));
            graph[conn.get(1)].add(conn.get(0));
        }
        for (int i = 0; i < n; i++) {
            if (dfs[i] == -1) {//等于-1时是没有访问过
                dfs(i, -1, dfs, low, graph, bridges);
            }
        }
        return bridges;
    }

    private void dfs(int cur, int parent, int[] dfs, int[] low, List<Integer>[] graph, List<List<Integer>> bridges) {
        dfs[cur] = low[cur] = id++;
        for (int adjoin : graph[cur]) {
            if (adjoin == parent) continue;
            if (dfs[adjoin] == -1) {
                dfs(adjoin, cur, dfs, low, graph, bridges);
                low[cur] = Math.min(low[cur], low[adjoin]);
                if (dfs[cur] < low[adjoin]) {
                    bridges.add(List.of(cur, adjoin));
                }
            } else {
                low[cur] = Math.min(low[cur], dfs[adjoin]);
            }
        }
    }
}
