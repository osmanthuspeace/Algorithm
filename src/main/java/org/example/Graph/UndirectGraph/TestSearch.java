package org.example.Graph.UndirectGraph;

import edu.princeton.cs.algs4.DepthFirstSearch;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/15
 */
public class TestSearch {
    public static void main(String[] args) {
        int[][] ver = {{0, 5}, {4, 3}, {0, 1}, {9, 12}, {6, 4}, {5, 4}, {0, 2}, {11, 12}, {9, 10}, {0, 6}, {7, 8}, {9, 11}, {5, 3}};
        var G = new MyGraph(13, 13, ver);
        var search = new DepthFirstSearch(G, 9);

        for (int v = 0; v < G.V(); v++) {
            if (search.marked(v)) { //判断顶点v和顶点9是否联通
                System.out.print(v + " ");
            }
        }
        System.out.println();
        if (search.count() != G.V()) {
            System.out.print("NOT ");
        }
        System.out.println("connected");
    }
}
