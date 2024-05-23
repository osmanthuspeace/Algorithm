package org.example.Graph.DirectGraph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/19
 */
//Kahn算法进行拓扑排序
public class PopularTopologicalSort {
    private final DirectGraph G;
    private final int[] inDegree;//表示节点的入度
    private final Queue<Integer> queue;
    private final ArrayList<Integer> result;

    public PopularTopologicalSort(int vertices) {
        G = new DirectGraph(vertices);
        inDegree = new int[G.V()];
        queue = new ArrayDeque<>();
        result = new ArrayList<>();
    }

    public void init(int[][] connects) {
        for (var conn : connects) {
            G.addEdge(conn[0], conn[1]);
            inDegree[conn[1]]++;
        }
        for (int v = 0; v < G.V(); v++) {
            if (inDegree[v] == 0) {//找到入度为0的节点，作为起始节点（没有任何前置条件）
                queue.offer(v);
            }
        }
    }

    public ArrayList<Integer> findTopologicalSort() {
        int count = 0;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            result.add(cur);
            count++;
            for (int v : G.adj(cur)) {
                inDegree[v]--;//当cur被弹出队列，相当于v的父节点少了一个，入度减1
                if (inDegree[v] == 0) {
                    queue.offer(v);//将入度为0的节点加入队列
                }
            }
        }
        if (count != G.V()) {//若存在环，则环中节点的入度无法减为0
            return null;
        }
        return result;
    }
}
