package org.example.Graph.WeightingGraph;

import edu.princeton.cs.algs4.IndexMinPQ;

import java.util.ArrayDeque;
import java.util.Arrays;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/28
 */
//Prim算法的即时实现
public class MyPrimMST {

    //shortestEdgeToMST[w]是指w距离树最近的边
    private final MyEdge[] shortestEdgeToMST;

    //shortestWeight[w]=shortestEdgeToMST[w].weight，即这条边的权重
    //即如果v不在树中但是至少有一条边与树相连，那么shortestEdgeToMST[v]是v和树连接的最短边，shortestEdgeToMST[v]是这条边的权重，所有这样的v会保存在队列中
    private final double[] shortestWeight;

    //marked[v]=true表示节点v在最小生成树中
    private final boolean[] marked;

    //使用索引优先队列储存有效的横切边，存的是shortestEdgeToMST[v]边的权重，即shortestWeight[v]，索引是节点的值
    private final IndexMinPQ<Double> pq;
    //本来LazyPrim算法中的队列要存储(树中所有节点)的邻接节点，现在只要存储(非树节点)中能将它和树连起来的最小节点
    //优先队列中的最小键是最小的横切边的权重，和它关联的顶点v就是下一个将被添加到树的节点

    public MyPrimMST(MyEdgeWeightedGraph G) {
        shortestEdgeToMST = new MyEdge[G.V()];
        shortestWeight = new double[G.V()];
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            shortestWeight[v] = Double.POSITIVE_INFINITY;
        }
        pq = new IndexMinPQ<>(G.V());

        shortestWeight[0] = 0.0;
        pq.insert(0, 0.0);//用顶点0和权重0初始化pq
        while (!pq.isEmpty()) {
            visit(G, pq.delMin());//每次搜索权重最小的横切边（delMin返回的是索引）
        }
    }

    //将当前顶点cur添加到树中，更新数据
    private void visit(MyEdgeWeightedGraph G, int cur) {
        //此时的cur是树中的最小节点
        marked[cur] = true;
        for (var e : G.adj(cur)) {
            int adjoin = e.otherNode(cur);
            //之前的LazyPrim算法会在此时将所有的邻接节点加入队列中
            if (marked[adjoin]) continue;//v-w边失效，跳过
            //遍历当前节点的所有邻接节点，如果有一个邻接节点adjoin的权重小于adjoin距离树最近的边的权重，就将最近距离更新成更小的那个
            if (e.weight() < shortestWeight[adjoin]) {
                shortestEdgeToMST[adjoin] = e;
                shortestWeight[adjoin] = e.weight();
                if (pq.contains(adjoin)) {
                    pq.changeKey(adjoin, shortestWeight[adjoin]);
                } else {
                    pq.insert(adjoin, shortestWeight[adjoin]);//自动排序之后，下一次从最小的边开始搜索
                }
            }
        }
    }

    //返回最小生成树
    public Iterable<MyEdge> edges() {
        return new ArrayDeque<>(
                Arrays.asList(shortestEdgeToMST).subList(1, shortestEdgeToMST.length)
        );
    }

}
