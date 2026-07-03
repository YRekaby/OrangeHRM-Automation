package Utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.util.Map;

public class JsonFileManager {

    private static Map<String, Object> jsonData;

    static {

        try {

            jsonData =
                    new Gson().fromJson(new FileReader("src/test/resources/config/config.json"), new TypeToken<Map<String, Object>>(){}.getType());

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to load config.json", e);
        }
    }

    public static String getValue(String key) {

        return jsonData.get(key).toString();
    }
}