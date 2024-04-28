package org.example;


import edu.princeton.cs.algs4.BinarySearchST;
import org.example.DataStructure.LinkedListBasedSequentialSearchSymbolTable;


import java.io.IOException;

import static org.example.Utility.ReadFileAsString.readFileAsString;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        int minLen = 3;
        LinkedListBasedSequentialSearchSymbolTable<String, Integer> st = new LinkedListBasedSequentialSearchSymbolTable<>();
        String path = "tale.txt";
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
    }

}