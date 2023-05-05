package com.litcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Compilers {
    public static CodeResponse compilePython(String problemId, String userName, String fileLang, Object[] expected, String returnType) {
        String[] printed = new String[0];
        try {
            List<String> runCommand = new ArrayList<>();
            runCommand.add("python");
            runCommand.add(String.format("./res/users/%s/problems/%s/%s", userName, problemId, fileLang));

            ProcessBuilder pb = new ProcessBuilder(runCommand);
            Process process = pb.start();

            List<String> errorLines = new ArrayList<>();
            final List<String> printedList = new ArrayList<>();

            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                errorReader.lines().forEach(line -> {
                    errorLines.add(line);
                    System.out.println(line);
                });
            }

            int exitCode = process.waitFor();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                reader.lines().limit(16).forEach(line -> {
                    printedList.add(line);
                    System.out.println(line);
                });
                if (printedList.size() > 15) {
                    System.out.println("Throwing a \"Too much prints\" error");
                }
            }
            if (printedList.size() > 15)
                printed = printedList.subList(0, 15).toArray(new String[0]);
            else
                printed = printedList.toArray(new String[0]);

            List<?> linesList = Files.lines(Paths.get("user.out")).map(line -> Converter.convert(line, returnType)).collect(Collectors.toList());
            Files.write(Paths.get("user.out"), "".getBytes());
            Object[] results = linesList.toArray();

            String printedString = String.join("\n", printed);
            String errorLineString = String.join("\n", errorLines);
            return new CodeResponse(results, expected, returnType, exitCode, results.length, printedString, errorLineString, exitCode == 0);
        } catch (IOException | InterruptedException e) {
            return new CodeResponse(new Object[0], new Object[0], "", -1, 0, String.join("\n", printed), e.toString(), false);
        }
    }

    // public static CodeResponse compileGolang(String problemId, String userName, String fileLang, Object[] expected, String returnType) {
    //     String[] printed = new String[0];
    //     try {
    //         List<String> runCommand = new ArrayList<>();
    //         runCommand.add("python");
    //         runCommand.add(String.format("./res/users/%s/problems/%s/%s", userName, problemId, fileLang));

    //         ProcessBuilder pb = new ProcessBuilder(runCommand);
    //         Process process = pb.start();

    //         List<String> errorLines = new ArrayList<>();
    //         List<String> dummyList = new ArrayList<>();
    //         String line;

    //         try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
    //             while ((line = errorReader.readLine()) != null) {
    //                 errorLines.add(line);
    //                 System.out.println(line);
    //             }
    //         }

    //         int exitCode = process.waitFor();

    //         try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
    //             while ((line = reader.readLine()) != null) {
    //                 if (expected.length + 10 > dummyList.size()) {
    //                     System.out.println("Throwing an \"Too much prints\" error");
    //                     break;
    //                 }
    //                 dummyList.add(line);
    //                 System.out.println(line);
    //             }
    //         }

    //         int dummyListSize = dummyList.size();
    //         String[] results = dummyList.subList(dummyListSize - expected.length, dummyListSize).toArray(new String[0]);
    //         printed = dummyList.subList(0, dummyListSize - expected.length).toArray(new String[0]);

    //         String printedString = String.join("\n", printed);
    //         String errorLineString = String.join("\n", errorLines);
    //         return new CodeResponse(results, expected, returnType, exitCode, results.length, printedString, errorLineString, exitCode == 0);
    //     } catch (IOException | InterruptedException e) {
    //         return new CodeResponse(new Object[0], new Object[0], "", -1, 0, String.join("\n", printed), e.toString(), false);
    //     }
    // }

    //     public static CodeResponse compileCpp(String problemId, String userName, String fileLang) {
    //         try {
    //             ProcessBuilder pb = new ProcessBuilder(
    //                 "python", String.format("./res/users/%s/problems/%s/%s", userName, problemId, fileLang));
    //             Process process = pb.start();

    //             List<String> results = new ArrayList<>();
    //             try (BufferedReader reader = new BufferedReader(
    //                      new InputStreamReader(process.getInputStream()))) {
    //                 String line;
    //                 while ((line = reader.readLine()) != null) {
    //                     results.add(line);
    //                     System.out.println(line);
    //                 }
    //             }

    //             int exitCode = process.waitFor();

    //             return new CodeResponse(results.toArray(), null, null, exitCode,
    //                 results.size(), "", exitCode == 0);
    //         } catch (IOException | InterruptedException e) {
    //             return new CodeResponse(new Object[0], new Object[0], int.class, -1, 0,
    //                 e.toString(), false);
    //         }
    //     }

    //     public static CodeResponse compileCsharp(String problemId, String userName, String fileLang) {
    //         try {
    //             ProcessBuilder pb = new ProcessBuilder(
    //                 "python", String.format("./res/users/%s/problems/%s/%s", userName, problemId, fileLang));
    //             Process process = pb.start();

    //             List<String> results = new ArrayList<>();
    //             try (BufferedReader reader = new BufferedReader(
    //                      new InputStreamReader(process.getInputStream()))) {
    //                 String line;
    //                 while ((line = reader.readLine()) != null) {
    //                     results.add(line);
    //                     System.out.println(line);
    //                 }
    //             }

    //             int exitCode = process.waitFor();

    //             return new CodeResponse(results.toArray(), null, null, exitCode,
    //                 results.size(), "", exitCode == 0);
    //         } catch (IOException | InterruptedException e) {
    //             return new CodeResponse(new Object[0], new Object[0], int.class, -1, 0,
    //                 e.toString(), false);
    //         }
    //     }

    //     public static CodeResponse compileJava(String problemId, String userName, String fileLang) {
    //         try {
    //             ProcessBuilder pb = new ProcessBuilder(
    //                 "python", String.format("./res/users/%s/problems/%s/%s", userName, problemId, fileLang));
    //             Process process = pb.start();

    //             List<String> results = new ArrayList<>();
    //             try (BufferedReader reader = new BufferedReader(
    //                      new InputStreamReader(process.getInputStream()))) {
    //                 String line;
    //                 while ((line = reader.readLine()) != null) {
    //                     results.add(line);
    //                     System.out.println(line);
    //                 }
    //             }

    //             int exitCode = process.waitFor();

    //             return new CodeResponse(results.toArray(), null, null, exitCode,
    //                 results.size(), "", exitCode == 0);
    //         } catch (IOException | InterruptedException e) {
    //             return new CodeResponse(new Object[0], new Object[0], int.class, -1, 0,
    //                 e.toString(), false);
    //         }
    //     }

    //     public static CodeResponse compileJavascript(String problemId, String userName, String fileLang) {
    //         try {
    //             ProcessBuilder pb = new ProcessBuilder(
    //                 "python", String.format("./res/users/%s/problems/%s/%s", userName, problemId, fileLang));
    //             Process process = pb.start();

    //             List<String> results = new ArrayList<>();
    //             try (BufferedReader reader = new BufferedReader(
    //                      new InputStreamReader(process.getInputStream()))) {
    //                 String line;
    //                 while ((line = reader.readLine()) != null) {
    //                     results.add(line);
    //                     System.out.println(line);
    //                 }
    //             }

    //             int exitCode = process.waitFor();

    //             return new CodeResponse(results.toArray(), null, null, exitCode,
    //                 results.size(), "", exitCode == 0);
    //         } catch (IOException | InterruptedException e) {
    //             return new CodeResponse(new Object[0], new Object[0], int.class, -1, 0,
    //                 e.toString(), false);
    //         }
    //     }

    //     public static CodeResponse compileTypescript(String problemId, String userName, String fileLang) {
    //         try {
    //             ProcessBuilder pb = new ProcessBuilder(
    //                 "python", String.format("./res/users/%s/problems/%s/%s", userName, problemId, fileLang));
    //             Process process = pb.start();

    //             List<String> results = new ArrayList<>();
    //             try (BufferedReader reader = new BufferedReader(
    //                      new InputStreamReader(process.getInputStream()))) {
    //                 String line;
    //                 while ((line = reader.readLine()) != null) {
    //                     results.add(line);
    //                     System.out.println(line);
    //                 }
    //             }

    //             int exitCode = process.waitFor();

    //             return new CodeResponse(results.toArray(), null, null, exitCode,
    //                 results.size(), "", exitCode == 0);
    //         } catch (IOException | InterruptedException e) {
    //             return new CodeResponse(new Object[0], new Object[0], int.class, -1, 0,
    //                 e.toString(), false);
    //         }
    //     }
    // }
}