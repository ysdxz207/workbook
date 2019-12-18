package com.hupubao.workbook.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * 
 * @author Moses
 * @date 2017-10-30 17:51
 * 
 */
public class FileUtils {

    public static String readFile(String path) {

        if (!new File(path).exists()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(path), StandardCharsets.UTF_8)) {
            for (String str : stream.collect(Collectors.toList())) {
                sb.append(str + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public static boolean writeFile(String filepath, String content) {
        try {
            File file = new File(filepath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            return Files.write(Paths.get(filepath), content.getBytes(StandardCharsets.UTF_8)).toFile().exists();
        } catch (IOException e) {
            return false;
        }
    }

}
