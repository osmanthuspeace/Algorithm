package org.example;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        var s = new FixedCapacityStack<String>(2);
        s.push("1");
        s.push("2");
        s.push("3");
        s.push("4");
        for (var i : s) {
            System.out.println(i);
        }
        System.out.println(s.pop());
        s.pop();
        s.pop();
        s.pop();
        System.out.println(s.isEmpty());
        var test =new StackImplementWithLinkedList<Integer>();
        test.push(1);
//        System.out.println(test.pop());
        System.out.println(test.isEmpty());

    }
}