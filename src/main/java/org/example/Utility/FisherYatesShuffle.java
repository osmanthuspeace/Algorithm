package org.example.Utility;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * author: osmanthuspeace
 * createTime: 2024/4/17
 */
public class FisherYatesShuffle {
    public static ArrayList<Integer> fisherYatesShuffle(Integer[] arr) {
        var length = arr.length;
        Random random = new Random();
        var input = Arrays.stream(arr).collect(Collectors.toCollection(ArrayList::new));
        for (int i = length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            Collections.swap(input, i, index);
        }
        return input;
    }
}
