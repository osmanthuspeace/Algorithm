package org.example.Graph.UndirectGraph;

import edu.princeton.cs.algs4.Graph;

import java.util.ArrayList;
import java.util.List;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/15
 */
@SuppressWarnings("unchecked")
public class MyGraph extends Graph {

    private final int V; //顶点数目
    private int E;

    private final List<Integer>[] adjacency; //邻接表

    public MyGraph(int V) {
        super(V);
        this.V = V;
        this.E = 0;
        adjacency = (List<Integer>[]) new ArrayList[V]; // 初始化邻接表
        for (int i = 0; i < V; i++) {
            this.adjacency[i] = new ArrayList<>();//为每一个顶点的列表初始化
        }
    }

    public MyGraph(int V, int E, int[][] vertex) {
        this(V);
        for (int i = 0; i < E; i++) {
            addEdge(vertex[i][0], vertex[i][1]);
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(int v, int w) {
        adjacency[v].add(w);
        adjacency[w].add(v);
        E++;
    }

    //返回一个顶点的所有邻接的顶点
    public Iterable<Integer> adj(int v) {
        return adjacency[v];
    }

    public static int degree(MyGraph G, int v) {
        int degree = 0;
        for (int ignored : G.adj(v)) {
            degree++;
        }
        return degree;
    }

    public static int maxDegree(MyGraph G) {
        int max = 0, thisDegree;
        for (int v = 0; v < G.V(); v++) {
            thisDegree = degree(G, v);
            if (degree(G, v) > max) {
                max = thisDegree;
            }
        }
        return max;
    }

    public static double avgDegree(MyGraph G) {
        return (double) (2 * G.E()) / G.V();//计算顶点的平均度数
    }

    public String toString() {
        StringBuilder s = new StringBuilder(V + " vertices, " + E + " edges\n");
        for (int v = 0; v < V; v++) {
            s.append(v).append(": ");
            for (int w : this.adj(v)) {
                s.append(w).append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }
}


