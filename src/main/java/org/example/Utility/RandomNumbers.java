package org.example.Utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Random;

/**
 * author: osmanthuspeace
 * createTime: 2024/4/16
 */

public class RandomNumbers {
    public static void generateAndSaveNumbers(String filename, int count) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        Path path = Paths.get(filename);
        for (int i = 0; i < count; i++) {
            // Generate a random integer and append it to the builder
            builder.append(random.nextInt(0, 100))
                    .append(System.lineSeparator());
            //用StringBuilder动态的接受数字，批量的写入文件，避免了重复的IO操作
            if (i % 10000 == 0) {
                try {
                    // Write in chunks to reduce memory usage
                    Files.write(
                            path,
                            builder.toString().getBytes(),
                            StandardOpenOption.CREATE,//指定如果目标文件不存在，则创建一个新文件
                            StandardOpenOption.APPEND//指定将数据追加到文件末尾
                    );
                    builder.setLength(0); // Clear the builder
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // Write any remaining data
        try {
            Files.write(
                    path,
                    builder.toString().getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readAndPrintNumbers(String filename) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            for (String line : lines) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String filename = "numbers.txt";
        generateAndSaveNumbers(filename, 100000);
//        readAndPrintNumbers(filename);
    }
}
