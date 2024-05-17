package org.example.Graph.UndirectGraph;

import java.io.IOException;
import java.util.Scanner;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/17
 */

//练习：间隔的度数
public class DegreeOfSeparation {
    public static void main(String[] args) throws IOException {
        String fileName = "movies.txt";
        String separator = "/";
        var sg = new SymbolGraph(fileName, separator);
        var G = sg.G();
        var scan = new Scanner(System.in);
        String source = scan.next();
        if (!sg.contains(source)) {
            System.out.println(source + " is not in database");
            return;
        }
        int s = sg.index(source);
        var bfs = new MyBFS(G, s);
        String sink = scan.next();
        if (sg.contains(sink)) {
            int t = sg.index(sink);
            if (bfs.hasPathTo(t)) {
                for (int v : bfs.pathTo(t)) {
                    System.out.println("    " + sg.name(v));
                }
            } else {
                System.out.println("Not connected");
            }
        } else {
            System.out.println("Not in database");
        }
    }
}
