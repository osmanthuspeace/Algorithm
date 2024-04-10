package org.example;

import java.util.*;

import static org.example.Algorithm.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        int n, m;
        Scanner s = new Scanner(System.in);
        n = s.nextInt();
        m = s.nextInt();
        Josephus(n, m);


    }
}