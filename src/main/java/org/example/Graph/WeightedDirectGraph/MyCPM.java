package org.example.Graph.WeightedDirectGraph;

import edu.princeton.cs.algs4.*;

import java.util.Arrays;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/30
 */
//处理优先级限制下的并行任务调度问题的关键路径法（Critical Path Method）
public class MyCPM {
    public static void main(String[] args) {
        int N = StdIn.readInt();//任务个数
        StdIn.readLine();//读取换行
        var G = new EdgeWeightedDirectGraph(2 * N + 2);
        int start = 2 * N;//虚拟的起点，方便统一处理所有任务的开始
        int target = 2 * N + 1;
        for (int i = 0; i < N; i++) {
            var info = StdIn.readLine().split("\\s+");//每一行包含的信息是时耗和优先级
            System.out.println(Arrays.toString(info));
            var duration = Double.parseDouble(info[0]);//取出时耗
            G.addEdge(new DiEdge(i, i + N, duration));//每个任务 i 有两个顶点表示：i 表示任务的开始，i + N 表示任务的结束
            G.addEdge(new DiEdge(start, i, 0.0));//从起点指向任务起始节点的边权重为0
            G.addEdge(new DiEdge(i + N, target, 0.0));//从任务结束顶点指向终点的边权重为0

            for (int j = 1; j < info.length; j++) {//取出优先级信息
                int successor = Integer.parseInt(info[j]);
                G.addEdge(new DiEdge(i + N, successor, 0.0));
            }
        }
        // 使用AcyclicSP求解最长路径
        MyAcyclicSP lp = new MyAcyclicSP(G, start);//要把针对自己的有向加权图的拓扑排序完成才能得到正确结果
//        System.out.println("Start times: ");
//        for (int i = 0; i < N; i++) {
//            StdOut.printf("%4d: %5.1f\n", i, lp.distTo(i));
//        }
//        StdOut.printf("Finish time: %5.1f\n", lp.distTo(target));
    }
}
/*
10
41.0  1 7 9
51.0  2
50.0
36.0
38.0
45.0
21.0  3 8
32.0  3 8
32.0  2
29.0  4 6
 */