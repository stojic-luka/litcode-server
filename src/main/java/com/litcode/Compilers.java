package com.litcode;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Compilers {
    // private static final String pythonCompilerDir = "./res/compilers/python";
    private static final String golangCompilerDir = "./res/compilers/golang/bin/go.exe";
    // private static final String cppCompilerDir = "./res/compilers/cpp";
    // private static final String csharpCompilerDir = "./res/compilers/csharp";
    // private static final String javaCompilerDir = "./res/compilers/java";
    // private static final String javascriptCompilerDir = "./res/compilers/javascript";
    // private static final String typescriptCompilerDir = "./res/compilers/typescript";

    public static CodeResponse compilePython(String problemId, String userName, String fileLang, Object[] expected, String returnType) {
        List<String> printedList = new ArrayList<>();
        try {
            List<String> runCommand = new ArrayList<>();
            runCommand.add("python");
            runCommand.add(String.format("./res/users/%s/problems/%s/%s", userName, problemId, fileLang));
            Process process = new ProcessBuilder(runCommand).start();
            int exitCode = process.waitFor();

            List<String> errorLines = new ArrayList<>();
            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                errorReader.lines().forEach(line -> errorLines.add(line));
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                reader.lines().limit(15).forEach(line -> printedList.add(line));
                if (printedList.size() > 14) {
                    System.out.println("Throwing a \"Too much prints\" error");
                }
            }

            List<?> linesList = Files.lines(Paths.get("user.out")).map(line -> Converter.convert(line, returnType)).collect(Collectors.toList());
            Object[] results = linesList.toArray();
            Files.write(Paths.get("user.out"), "".getBytes());

            String printedString = String.join("\n", printedList);
            String errorLineString = String.join("\n", errorLines);
            return new CodeResponse(results, expected, returnType, exitCode, results.length, printedString, errorLineString, exitCode == 0);
        } catch (IOException | InterruptedException e) {
            return new CodeResponse(new Object[0], new Object[0], "", -1, 0, String.join("\n", printedList), e.toString(), false);
        }
    }

    public static CodeResponse compileGolang(String problemId, String userName, String fileLang, Object[] expected, String returnType) {
        List<String> printedList = new ArrayList<>();
        try {
            List<String> runCommand = new ArrayList<>();
            runCommand.add(String.format("%s", golangCompilerDir));
            runCommand.add("run");
            runCommand.add(String.format("./res/users/%s/problems/%s/%s", userName, problemId, fileLang));
            Process process = new ProcessBuilder(runCommand).start();
            int exitCode = process.waitFor();

            List<String> errorLines = new ArrayList<>();
            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                errorReader.lines().forEach(line -> errorLines.add(line));
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                reader.lines().limit(15).forEach(line -> printedList.add(line));
                if (printedList.size() > 14) {
                    System.out.println("Throwing a \"Too much prints\" error");
                }
            }

            List<?> linesList = Files.lines(Paths.get("user.out")).map(line -> Converter.convert(line, returnType)).collect(Collectors.toList());
            Files.write(Paths.get("user.out"), "".getBytes());
            Object[] results = linesList.toArray();

            String printedString = String.join("\n", printedList);
            String errorLineString = String.join("\n", errorLines);
            return new CodeResponse(results, expected, returnType, exitCode, results.length, printedString, errorLineString, exitCode == 0);
        } catch (IOException | InterruptedException e) {
            return new CodeResponse(new Object[0], new Object[0], "", -1, 0, String.join("\n", printedList), e.toString(), false);
        }
    }

    public static CodeResponse compileCpp(String problemId, String userName, String fileLang, Object[] expected, String returnType) {
        List<String> compilerErrorList = new ArrayList<>(Arrays.asList("Compile errors:"));
        List<String> printedList = new ArrayList<>();
        try {
            List<String> runCommandBuild = new ArrayList<>();
            runCommandBuild.add("g++");
            runCommandBuild.add(String.format("-o ./res/users/%s/problems/%s/main", userName, problemId));
            runCommandBuild.add(String.format("./res/users/%s/problems/%s/%s", userName, problemId, fileLang));
            Process processBuild = new ProcessBuilder(runCommandBuild).start();
            int exitCode = processBuild.waitFor();

            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(processBuild.getErrorStream()))) {
                errorReader.lines().forEach(line -> compilerErrorList.add(line));
            }
            if (exitCode != 0)
                return new CodeResponse(new Object[0], new Object[0], "", exitCode, 0, "", String.join("\n", compilerErrorList), false);

            List<String> runCommand = new ArrayList<>();
            runCommand.add(String.format("./res/users/%s/problems/%s/main.exe", userName, problemId, fileLang));
            Process process = new ProcessBuilder(runCommand).start();
            exitCode = process.waitFor();

            List<String> errorLines = new ArrayList<>();
            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                errorReader.lines().forEach(line -> errorLines.add(line));
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                reader.lines().limit(15).forEach(line -> printedList.add(line));
                if (printedList.size() > 14) {
                    System.out.println("Throwing a \"Too much prints\" error");
                }
            }

            List<?> linesList = Files.lines(Paths.get("user.out")).map(line -> Converter.convert(line, returnType)).collect(Collectors.toList());
            Files.write(Paths.get("user.out"), "".getBytes());
            Object[] results = linesList.toArray();

            String printedString = String.join("\n", printedList);
            String errorLineString = String.join("\n", errorLines);
            return new CodeResponse(results, expected, returnType, exitCode, results.length, printedString, errorLineString, exitCode == 0);
        } catch (IOException | InterruptedException e) {
            return new CodeResponse(new Object[0], new Object[0], "", -1, 0, String.join("\n", printedList), e.toString(), false);
        }
    }

    public static CodeResponse compileCsharp(String problemId, String userName, String fileLang, Object[] expected, String returnType) {
        try {
            List<String> runCommandBuild = new ArrayList<>();
            runCommandBuild.add("C:/Windows/Microsoft.NET/Framework/v4.0.30319/csc.exe");
            // runCommandBuild.add(String.format("-o ./res/users/%s/problems/%s/", userName, problemId));
            runCommandBuild.add("/out:main.exe");
            runCommandBuild.add(String.format("./res/users/%s/problems/%s/%s", userName, problemId, fileLang));

            Process processBuild = new ProcessBuilder(runCommandBuild).start();
            processBuild.waitFor();

            System.out.println(12345);
            return new CodeResponse();
        } catch (IOException | InterruptedException e) {
            return new CodeResponse();
        }
    }
    // public static CodeResponse compileCsharp(String problemId, String userName, String fileLang, Object[] expected, String returnType) {
    //     String[] printed = new String[0];
    //     try {
    //         List<String> runCommandBuild = new ArrayList<>();
    //         runCommandBuild.add("dotnet");
    //         runCommandBuild.add("build");
    //         runCommandBuild.add(String.format("-o ./res/users/%s/problems/%s/%s", userName, problemId, "csharp.exe"));
    //         runCommandBuild.add("-c Release");
    //         runCommandBuild.add(String.format("./res/users/%s/problems/%s/%s", userName, problemId, fileLang));

    //         Process processBuild = new ProcessBuilder(runCommandBuild).start();
    //         processBuild.waitFor();

    //         System.out.println(12345);

    //         List<String> runCommand = new ArrayList<>();
    //         runCommand.add("dotnet");
    //         runCommand.add("run");
    //         runCommand.add(String.format("./res/users/%s/problems/%s/%s", userName, problemId, fileLang));

    //         Process process = new ProcessBuilder(runCommand).start();

    //         List<String> errorLines = new ArrayList<>();
    //         final List<String> printedList = new ArrayList<>();

    //         int exitCode = process.waitFor();

    //         try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
    //             errorReader.lines().forEach(line -> {
    //                 errorLines.add(line);
    //                 System.out.println(line);
    //             });
    //         }

    //         try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
    //             reader.lines().limit(16).forEach(line -> {
    //                 printedList.add(line);
    //                 System.out.println(line);
    //             });
    //             if (printedList.size() > 15) {
    //                 System.out.println("Throwing a \"Too much prints\" error");
    //             }
    //         }
    //         if (printedList.size() > 15)
    //             printed = printedList.subList(0, 15).toArray(new String[0]);
    //         else
    //             printed = printedList.toArray(new String[0]);

    //         List<?> linesList = Files.lines(Paths.get("user.out")).map(line -> Converter.convert(line, returnType)).collect(Collectors.toList());
    //         Files.write(Paths.get("user.out"), "".getBytes());
    //         Object[] results = linesList.toArray();

    //         String printedString = String.join("\n", printed);
    //         String errorLineString = String.join("\n", errorLines);
    //         return new CodeResponse(results, expected, returnType, exitCode, results.length, printedString, errorLineString, exitCode == 0);
    //     } catch (IOException | InterruptedException e) {
    //         return new CodeResponse(new Object[0], new Object[0], "", -1, 0, String.join("\n", printed), e.toString(), false);
    //     }
    // }

    public static CodeResponse compileJava(String problemId, String userName, String fileLang, Object[] expected, String returnType) {
        List<String> compilerErrorList = new ArrayList<>(Arrays.asList("Compile errors:"));
        List<String> printedList = new ArrayList<>();
        try {
            List<String> runCommandBuild = new ArrayList<>();
            runCommandBuild.add("javac");
            runCommandBuild.add(String.format("./res/users/%s/problems/%s/%s", userName, problemId, fileLang));
            Process processBuild = new ProcessBuilder(runCommandBuild).start();
            int exitCode = processBuild.waitFor();

            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(processBuild.getErrorStream()))) {
                errorReader.lines().forEach(line -> compilerErrorList.add(line));
            }
            if (exitCode != 0)
                return new CodeResponse(new Object[0], new Object[0], "", exitCode, 0, "", String.join("\n", compilerErrorList), false);

            List<String> runCommand = new ArrayList<>();
            runCommand.add("java");
            runCommand.add(String.format("./res/users/%s/problems/%s/%s", userName, problemId, fileLang));
            Process process = new ProcessBuilder(runCommand).start();
            exitCode = process.waitFor();

            List<String> errorLines = new ArrayList<>();
            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                errorReader.lines().forEach(line -> errorLines.add(line));
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                reader.lines().limit(15).forEach(line -> printedList.add(line));
                if (printedList.size() > 14) {
                    System.out.println("Throwing a \"Too much prints\" error");
                }
            }

            List<?> linesList = Files.lines(Paths.get("user.out")).map(line -> Converter.convert(line, returnType)).collect(Collectors.toList());
            Files.write(Paths.get("user.out"), "".getBytes());
            Object[] results = linesList.toArray();

            new File(String.format("./res/users/%s/problems/%s/Main.class", userName, problemId)).delete();
            new File(String.format("./res/users/%s/problems/%s/Main$Solution.class", userName, problemId)).delete();

            String printedString = String.join("\n", printedList);
            String errorLineString = String.join("\n", errorLines);
            return new CodeResponse(results, expected, returnType, exitCode, results.length, printedString, errorLineString, exitCode == 0);
        } catch (IOException | InterruptedException e) {
            return new CodeResponse(new Object[0], new Object[0], "", -1, 0, String.join("\n", printedList), e.toString(), false);
        }
    }

    public static CodeResponse compileJavascript(String problemId, String userName, String fileLang, Object[] expected, String returnType) {
        final List<String> printedList = new ArrayList<>();
        try {
            List<String> runCommand = new ArrayList<>();
            runCommand.add("node");
            runCommand.add(String.format("./res/users/%s/problems/%s/%s", userName, problemId, fileLang));
            Process process = new ProcessBuilder(runCommand).start();
            int exitCode = process.waitFor();

            List<String> errorLines = new ArrayList<>();
            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                errorReader.lines().forEach(line -> {
                    errorLines.add(line);
                    System.out.println(line);
                });
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                reader.lines().limit(15).forEach(line -> {
                    printedList.add(line);
                    System.out.println(line);
                });
                if (printedList.size() > 14) {
                    System.out.println("Throwing a \"Too much prints\" error");
                }
            }

            List<?> linesList = Files.lines(Paths.get("user.out")).map(line -> Converter.convert(line, returnType)).collect(Collectors.toList());
            Files.write(Paths.get("user.out"), "".getBytes());
            Object[] results = linesList.toArray();

            String printedString = String.join("\n", printedList);
            String errorLineString = String.join("\n", errorLines);
            return new CodeResponse(results, expected, returnType, exitCode, results.length, printedString, errorLineString, exitCode == 0);
        } catch (IOException | InterruptedException e) {
            return new CodeResponse(new Object[0], new Object[0], "", -1, 0, String.join("\n", printedList), e.toString(), false);
        }
    }

    public static CodeResponse compileTypescript(String problemId, String userName, String fileLang, Object[] expected, String returnType) {
        final List<String> printedList = new ArrayList<>();
        try {
            List<String> runCommand = new ArrayList<>();
            runCommand.add("tsc");
            runCommand.add(String.format("./res/users/%s/problems/%s/%s", userName, problemId, fileLang));
            Process process = new ProcessBuilder(runCommand).start();
            int exitCode = process.waitFor();

            List<String> errorLines = new ArrayList<>();
            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                errorReader.lines().forEach(line -> {
                    errorLines.add(line);
                    System.out.println(line);
                });
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                reader.lines().limit(15).forEach(line -> {
                    printedList.add(line);
                    System.out.println(line);
                });
                if (printedList.size() > 14) {
                    System.out.println("Throwing a \"Too much prints\" error");
                }
            }

            List<?> linesList = Files.lines(Paths.get("user.out")).map(line -> Converter.convert(line, returnType)).collect(Collectors.toList());
            Files.write(Paths.get("user.out"), "".getBytes());
            Object[] results = linesList.toArray();

            String printedString = String.join("\n", printedList);
            String errorLineString = String.join("\n", errorLines);
            return new CodeResponse(results, expected, returnType, exitCode, results.length, printedString, errorLineString, exitCode == 0);
        } catch (IOException | InterruptedException e) {
            return new CodeResponse(new Object[0], new Object[0], "", -1, 0, String.join("\n", printedList), e.toString(), false);
        }
    }
}