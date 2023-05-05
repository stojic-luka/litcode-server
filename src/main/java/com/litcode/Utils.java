package com.litcode;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utils {
    private static final Gson gson = new Gson();

    public static boolean setupNewProblemPerUser(String problemId, String userName) {
        try {
            String[] jsonFiles = new String[] {"python", "golang", "cpp", "csharp", "java", "javascript", "typescript"};
            for (String jsonFile : jsonFiles) {
                String fileDir = String.format("./res/problems/%s/%s", problemId, jsonFile + ".json");
                String jsonString = new String(Files.readAllBytes(Paths.get(fileDir)));
                JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

                String[] sourceCodeLines = gson.fromJson(jsonObject.get("source_code"), String[].class);
                String fileName = jsonObject.get("main_file_name").getAsString();
                fileDir = String.format("./res/users/%s/problems/%s/%s", userName, problemId, fileName);
                try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileDir))) {
                    for (String line : sourceCodeLines) {
                        writer.write(line + "\n");
                    }
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean copyCodeBeforeCompiling(String problemId, String fileLang, String user, String[] userCode, JsonObject jsonObject) {
        try {
            Path destinationFilePath = Paths.get(String.format("./res/users/%s/problems/%s/%s", user, problemId, fileLang));
            int insertOnLine = jsonObject.get("insert_on_row").getAsInt();
            String[] defaultCode = gson.fromJson(jsonObject.get("default_code"), String[].class);
            Logger.logInfo("123");

            try (BufferedWriter writer = Files.newBufferedWriter(destinationFilePath)) {
                for (int i = 0; i < defaultCode.length; i++) {
                    if (i == insertOnLine)
                        for (String userCodeLine : userCode) writer.write(userCodeLine.replaceAll("\t", "    ") + "\n");
                    writer.write(defaultCode[i] + "\n");
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
