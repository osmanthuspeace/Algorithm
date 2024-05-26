package org.example.Graph.UndirectGraph;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdIn;
import org.example.Utility.ReadFile;

import java.io.IOException;
import java.util.List;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/17
 */
public class SymbolGraph {

    private final ST<String, Integer> st;//符号表，映射关系为：顶点名-索引
    private final String[] keys;//反向索引，将索引对应顶点名
    private final Graph G;

    public SymbolGraph(String fileName, String separator) throws IOException {
        st = new ST<>();
        List<String> fileContent = ReadFile.readFileLinesAsStringList(fileName);//将文件一行一行读，因为此处的信息是以行为单位给出的
        for (var line : fileContent) {
            String[] a = line.split(separator);//用给定的分隔符分隔每一行的信息
            for (String s : a) {
                if (!st.contains(s)) {
                    st.put(s, st.size());//将每一个单词对应一个自增的数字，将所有的顶点插入符号表
                }
            }
        }
        keys = new String[st.size()];
        for (String name : st.keys()) {
            keys[st.get(name)] = name;//初始化反向索引
        }
        G = new Graph(st.size());
        for (var line : fileContent) {
            String[] a = line.split(separator);
            int v = st.get(a[0]);//将每一行的第一个顶点与该行放入其他顶点相连
            for (String s : a) {
                G.addEdge(v, st.get(s));
            }
        }
    }

    public boolean contains(String s) {
        return st.contains(s);
    }

    public int index(String s) {
        return st.get(s);
    }

    public String name(int v) {
        return keys[v];
    }

    public Graph G() {
        return G;
    }

    public static void main(String[] args) throws IOException {
        edu.princeton.cs.algs4.SymbolGraph s = new edu.princeton.cs.algs4.SymbolGraph("movies.txt", "/");
        Graph G = s.G();
        while (StdIn.hasNextLine()) {
            String source = StdIn.readLine();
            for (int w : G.adj(s.index(source))) {
                System.out.println("    " + s.name(w));
            }
        }
    }
}
