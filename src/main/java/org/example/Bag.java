package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Bag<E> {
    private final Map<E, Integer> map = new HashMap<>();

    // 添加元素
    public void add(E element) {
        map.put(element, map.getOrDefault(element, 0) + 1);
    }

    // 检查元素数量
    public int count(E element) {
        return map.getOrDefault(element, 0);
    }

    // 移除元素
    public void remove(E element) {
        if (map.containsKey(element)) {
            int count = map.get(element);
            if (count == 1) {
                map.remove(element);
            } else {
                map.put(element, count - 1);
            }
        }
    }

    // 获取所有元素的集合，包括重复元素
    public Set<E> elementSet() {
        return map.keySet();
    }

    // 获取Bag的大小（包括重复的元素）
    public int size() {
        int size = 0;
        for (E element : map.keySet()) {
            size += map.get(element);
        }
        return size;
    }

    // 打印Bag中的所有元素和它们的数量
    public void printElements() {
        for (Map.Entry<E, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
