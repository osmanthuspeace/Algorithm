package org.example;


import edu.princeton.cs.algs4.ST;

import java.io.IOException;

import static org.example.Utility.ReadFileAsString.readFileAsString;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        int minLen = 3;
        ST<String, Integer> st = new ST<>();
//        OrderedArrayBasedBinarySearchST<String, Integer> st = new OrderedArrayBasedBinarySearchST<>();
//        LinkedListBasedSequentialSearchSymbolTable<String,Integer> st=new LinkedListBasedSequentialSearchSymbolTable<>();
//        MyBinarySearchTree<String,Integer> st=new MyBinarySearchTree<>();
        String path = "tale.txt";
        double f=1.0;
        System.out.println(Double.hashCode(f));

        try {
            var s = readFileAsString(path);
            String[] words = s.split("\\s+");
            for (var word : words) {
                if (word.length() < minLen) continue;
                if (st.contains(word)) st.put(word, st.get(word) + 1);
                else st.put(word, 1);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        int max = 0;
        String flag = "";
        for (var word : st) {
            if (st.get(word) > max) {
                max = st.get(word);
                flag = word;
            }
            System.out.println(word + " : " + st.get(word));
        }
        System.out.println(flag + " : " + max);

        int a = 30;
        System.out.println(a >>> 2);
    }
}