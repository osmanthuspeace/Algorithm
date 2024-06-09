package org.example.DataStructure;

/**
 * author: osmanthuspeace
 * createTime: 2024/6/2
 */
public record Pair<F, S>(F first, S second) {

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}

