package org.example.Graph.WeightedDirectGraph;

import edu.princeton.cs.algs4.StdIn;

/**
 * author: osmanthuspeace
 * createTime: 2024/6/1
 */
public class MyArbitrage {
    public static void main(String[] args) {
        int V = StdIn.readInt();//货币种数
        var name = new String[V];
        var G = new EdgeWeightedDirectGraph(V);
        for (int v = 0; v < V; v++) {
            name[v] = StdIn.readString();//货币名称
            for (int w = 0; w < V; w++) {
                var rate = StdIn.readDouble();
                var e = new DiEdge(v, w, -Math.log(rate));
                G.addEdge(e);
            }
        }
        var spt = new MyBellmanFordSP(G, 0);
        if (spt.hasNegativeCycle()) {//如果有负权环，说明有套汇的机会，在我的实现中只能找到第一个负权环
            var stake = 1000.0;//本金
            for (var e : spt.negativeCycle()) {
                System.out.printf("%10.5f %s", stake, name[e.from()]);
                stake *= Math.exp(-e.weight());//恢复原汇率
                System.out.printf("= %10.5f %s\n", stake, name[e.to()]);
            }
        } else {
            System.out.println("No arbitrage opportunity");
        }
    }
}
