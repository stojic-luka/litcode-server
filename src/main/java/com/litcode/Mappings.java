package com.litcode;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Mappings {
    private static Gson gson = new Gson();

    @GetMapping("/")
    public String helloworld() {
        return "Hello world";
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/compile")
    public ResponseEntity<String> checkCompilation(@RequestBody String request) {
        JsonObject jsonObject = JsonParser.parseString(request).getAsJsonObject();
        if (!jsonObject.has("id") || !jsonObject.has("source_code") || !jsonObject.has("lang"))
            return new ResponseEntity<>("Data missing", HttpStatus.BAD_REQUEST);

        try {
            String problemId = jsonObject.get("id").getAsString();
            String userSourceCode = jsonObject.get("source_code").getAsString();
            String decodedUserSourceCode = new String(Base64.getDecoder().decode(userSourceCode));
            String usedLanguage = jsonObject.get("lang").getAsString().toLowerCase();

            String userName = "random_user"; // can be changed with user handling

            JsonObject jsonObjectSwitch;
            String fileDir, fileLang;
            Object[] expected;

            Logger.logInfo("Current directory: " + System.getProperty("user.dir"));

            CodeResponse resp = new CodeResponse();
            fileDir = String.format("./res/problems/%s/%s.json", problemId, usedLanguage);
            String jsonString = new String(Files.readAllBytes(Paths.get(fileDir)));
            jsonObjectSwitch = JsonParser.parseString(jsonString).getAsJsonObject();
            fileLang = String.format("main%s", jsonObjectSwitch.get("main_file_extention").getAsString());
            expected = gson.fromJson(jsonObjectSwitch.get("expected_values"), Object[].class);
            String returnType = jsonObjectSwitch.get("return_type").getAsString();
            Logger.logInfo(fileLang);

            if (Utils.copyCodeBeforeCompiling(problemId, fileLang, userName, decodedUserSourceCode.split("\n"), jsonObjectSwitch))
                switch (usedLanguage) {
                    case "python":
                        resp = Compilers.compilePython(problemId, userName, fileLang, expected, returnType);
                        break;
                    case "golang":
                        resp = Compilers.compileGolang(problemId, userName, fileLang, expected, returnType);
                        break;
                    case "cpp":
                        resp = Compilers.compileCpp(problemId, userName, fileLang, expected, returnType);
                        break;
                    case "csharp":
                        resp = Compilers.compileCsharp(problemId, userName, fileLang, expected, returnType);
                        break;
                    case "java":
                        resp = Compilers.compileJava(problemId, userName, fileLang, expected, returnType);
                        break;
                    case "javascript":
                        resp = Compilers.compileJavascript(problemId, userName, fileLang, expected, returnType);
                        break;
                    case "typescript":
                        resp = Compilers.compileTypescript(problemId, userName, fileLang, expected, returnType);
                        break;
                }
            else {
                System.out.println("CodeManipulation false error");
                resp = new CodeResponse();
            }
            Logger.logInfo(resp.toString());
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error occured", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Response body test", HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/defaultcode")
    public ResponseEntity<String> getDefaultCode(@RequestBody String request) {
        Logger.logInfo(request);
        JsonElement jsonElement = JsonParser.parseString(request);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        if (!jsonObject.has("id") || !jsonObject.has("lang"))
            return new ResponseEntity<>("Data missing", HttpStatus.BAD_REQUEST);

        String[] sourceCode;
        try {
            String problemId = jsonObject.get("id").getAsString();
            String usedLanguage = jsonObject.get("lang").getAsString().toLowerCase();
            String fileDir = String.format("./res/problems/%s/%s.json", problemId, usedLanguage);
            String jsonString = new String(Files.readAllBytes(Paths.get(fileDir)));
            JsonObject jsonObjectSwitch = JsonParser.parseString(jsonString).getAsJsonObject();
            sourceCode = gson.fromJson(jsonObjectSwitch.get("user_code"), String[].class);
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error occured", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(String.join("\n", sourceCode), HttpStatus.OK);
    }
}