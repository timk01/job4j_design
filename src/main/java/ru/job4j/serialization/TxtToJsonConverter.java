package ru.job4j.serialization;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TxtToJsonConverter {

    public static void main(String[] args) {
        String inputFilePath = "data/facebook_amnesia.txt";
        String outputFilePath = "data/facebook_amnesia.json";
        
        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
            JSONArray jsonArray = new JSONArray();
            String line;
            
            while ((line = br.readLine()) != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("hostname", line);
                jsonObject.put("ip", "");
                jsonArray.put(jsonObject);
            }
            
            try (FileWriter file = new FileWriter(outputFilePath)) {
                file.write(jsonArray.toString(4)); // 4 - отступ для форматирования JSON
                file.flush();
            }
            
            System.out.println("Successfully converted txt to json.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}