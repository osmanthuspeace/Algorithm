package org.example.Graph.DirectGraph;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/23
 */
//顶点对的可达性（只适用于小型的图，因为这个算法空间复杂度为V^2，而时间复杂度为V(V+E)）
public class TransitiveClosure {
    private final DirectDFS[] all;

    public TransitiveClosure(DirectGraph G) {
        all = new DirectDFS[G.V()];
        for (int v = 0; v < G.V(); v++) {
            all[v] = new DirectDFS(G, v);
        }
    }

    //通过计算G的传递闭包来支持常数级别时间的查询——传递闭包矩阵的第v行就是all[v]的marked数组
    public boolean reachable(int v, int w) {
        return all[v].marked(w);
    }
    //传递闭包是一个图，如果原图中存在从A到B的路径（可能经过许多节点），则在传递闭包中存在直接从A到B的边
}
