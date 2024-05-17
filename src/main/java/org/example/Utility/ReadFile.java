package org.example.Utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * author: osmanthuspeace
 * createTime: 2024/4/25
 */
public class ReadFile {
    //在项目文件夹中只要传入文件名，其他地方要传入完整路径
    public static String readFileAsString(String fileName) throws IOException {
        Path path = Paths.get(fileName).toAbsolutePath(); // 获取绝对路径
        return Files.readString(path); // 直接使用Path对象读取文件内容
    }
    public static List<String> readFileLinesAsStringList(String fileName) throws IOException {
        Path path = Paths.get(fileName).toAbsolutePath();
        return Files.readAllLines(path);
    }
}
