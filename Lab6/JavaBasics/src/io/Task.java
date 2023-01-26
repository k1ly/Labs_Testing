package io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Task {
    private final static String DIR_SEP = "|----- ";
    private final static String FILE_SEP = " | ";

    public static void main(String[] args) {
        String filename = args[0];
        File file = new File(filename);
        if (file.exists()) {
            try {
                if (file.isDirectory()) {
                    File dirInfo = new File(Path.of(filename, "info.txt").toString());
                    if (dirInfo.exists() && dirInfo.delete())
                        System.out.println("Файл info.txt удален");
                    if (dirInfo.createNewFile()) {
                        System.out.println("Файл info.txt создан");
                        try (FileWriter fileWriter = new FileWriter(dirInfo)) {
                            for (int i = 0; i < Objects.requireNonNull(file.listFiles()).length; i++) {
                                var child = Objects.requireNonNull(file.listFiles())[i];
                                if (child.isDirectory()) {
                                    fileWriter.write(DIR_SEP + child.getName() + "\n");
                                    for (int j = 0; j < Objects.requireNonNull(child.listFiles()).length; j++) {
                                        var descendant = Objects.requireNonNull(child.listFiles())[j];
                                        fileWriter.write(FILE_SEP + descendant.getName() + "\n");
                                    }
                                } else
                                    fileWriter.write(" " + child.getName() + "\n");
                            }
                        }
                    }
                } else {
                    if (file.getName().equals("info.txt")) {
                        System.out.println("Файл " + file.getName() + " распознан");
                        Map<String, Integer> dirInfo = new LinkedHashMap<>();
                        Scanner scanner = new Scanner(file);
                        int fileCount = 0;
                        int totalFileNameLength = 0;
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine();
                            if (line.startsWith(DIR_SEP)) {
                                dirInfo.put(line, 0);
                            } else if (line.startsWith(FILE_SEP)) {
                                var dir = dirInfo.entrySet().stream().toList().get(dirInfo.size() - 1);
                                dir.setValue(dir.getValue() + 1);
                                fileCount++;
                                totalFileNameLength += line.length() - FILE_SEP.length();
                            }
                        }
                        System.out.println("Количество папок: " + dirInfo.size());
                        System.out.println("Количетсво файлов: " + fileCount);
                        System.out.println("Среднее количетсво файлов: " + (fileCount / dirInfo.size()));
                        System.out.println("Средняя длина имени файла: " + (totalFileNameLength / fileCount));
                    }

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else System.out.println("Такого пути не существует");
    }
}
