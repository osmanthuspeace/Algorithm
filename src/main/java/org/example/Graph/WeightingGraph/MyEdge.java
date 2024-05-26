package org.example.Graph.WeightingGraph;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/26
 */
public class MyEdge implements Comparable<MyEdge> {

    private final int v;
    private final int w;
    private final double weight;

    public MyEdge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    //返回边的权重
    public double weight() {
        return weight;
    }

    //返回边的顶点之一
    public int oneNode() {
        return v;
    }

    //返回边的另一个顶点
    public int otherNode(int vertex) {
        if (vertex == v) return w;
        else if (vertex == w) return v;
        else throw new RuntimeException("Inconsistent edge");
    }

    @Override
    public int compareTo(MyEdge that) {
        return Double.compare(this.weight(), that.weight());
    }

    public String toString() {
        return String.format("%d-%d %.2f", v, w, weight);
    }
}
