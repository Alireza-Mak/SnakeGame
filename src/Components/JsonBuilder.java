package Components;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class JsonBuilder {
    private final String url = System.getProperty("user.dir") + "\\src\\records.json";
    private JSONObject recordsObject = new JSONObject();


    public void addNewRecord(String name, int score) {
        JSONObject newRecord = new JSONObject();
        newRecord.put("id", nextId());
        newRecord.put("name", name);
        newRecord.put("score", score);
        JSONObject all_records = getRecords();
        if (all_records.get("records") != null) {
            JSONArray recordsArray = (JSONArray) all_records.get("records");
            recordsArray.add(newRecord);
        } else {
            JSONArray recordsArray = new JSONArray();
            recordsArray.add(newRecord);
            all_records.put("records", recordsArray);
        }
        recordsObject = all_records;
        createJsonFile();
    }

    private int nextId() {
        int id;
        JSONObject records = getRecords();
        JSONArray recordsArray = (JSONArray) records.get("records");
        if (records.get("records") != null && !recordsArray.isEmpty()) {
            JSONObject lastRecord = (JSONObject) recordsArray.get(recordsArray.size() - 1);
            id = Integer.parseInt(lastRecord.get("id").toString());

        } else {
            id = 0;
        }
        return ++id;
    }

    public JSONObject getRecords() {
        File file = new File(url);
        JSONObject records = new JSONObject();
        if (file.exists()) {
            try {
                Object object = new JSONParser().parse(new FileReader(url));
                records = (JSONObject) object;

            } catch (IOException | ParseException e) {
                System.out.println(e.getMessage());
            }
        }
        return records;
    }

    public void createJsonFile() {
        try (FileWriter fileWriter = new FileWriter(url)) {
            fileWriter.write(prettierJson(recordsObject.toJSONString()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private String prettierJson(String unformattedJson) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(unformattedJson);
        return gson.toJson(je);
    }
}
