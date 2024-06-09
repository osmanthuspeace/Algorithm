package org.example;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello world!");
        Runnable test = () -> System.out.println("hello");
        test.run();
        String a = "12123";
        char c = a.charAt(1);
        String temp = a.substring(2);
        var te="1 12\\n13".translateEscapes();
        System.out.println(te);
    }
}