package com.zhf;
import java.util.Map;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.yaml.snakeyaml.Yaml;

/**
    simple YAML to JSON Converter 
    credit goes to https://github.com/stleary/JSON-java
 */
public final class Yaml2Json {
    private static final Logger logger = LogManager.getLogger(Yaml2Json.class);
    private static final String PATH = "<C:/<path to>/test.yml";

    private Yaml2Json() {
    }    

    // recursively transform elements to json
    private static Object convertToJson(Object o) throws JSONException {
        if (o instanceof Map) {
          Map<Object, Object> map = (Map<Object, Object>) o;    
          JSONObject result = new JSONObject();
    
          for (Map.Entry<Object, Object> stringObjectEntry : map.entrySet()) {
            String key = stringObjectEntry.getKey().toString();    
            result.put(key, convertToJson(stringObjectEntry.getValue()));
          }    
          return result;
        } else if (o instanceof ArrayList) {
          ArrayList arrayList = (ArrayList) o;
          JSONArray result = new JSONArray();
    
          for (Object arrayObject : arrayList) {
            result.put(convertToJson(arrayObject));
          }    
          return result;
        } else if (o instanceof String) {
          return o;
        } else if (o instanceof Boolean) {
          return o; 
        } else {
          logger.error("Unsupported class [{}]", o.getClass().getName());
          return o;
        }
    } 

    private static String readFile(String filePath) 
    {
        String content = "";
        try {
            content = new String ( Files.readAllBytes( Paths.get(filePath) ) );
            logger.debug("File ({}), contents:\n{}",filePath,content);
        } 
        catch (IOException e) {
            logger.error("Error reading file:{}, exception {}",filePath,e);
        } 
        return content;
    }    
    
    private static JSONObject string2Json(String s) 
    {
        Yaml yaml= new Yaml();
        Map<String,Object> map= (Map<String, Object>) yaml.load(s);        
        logger.debug("Mapped string {} \n", map);
        JSONObject jsonObject = (JSONObject) convertToJson(map);
        logger.debug("JSON Object {} \n", jsonObject);
        return jsonObject;
    } 

    public static void main(String[] args) {
        logger.debug("Entering main(), #of args {}",args.length);
        final String s = readFile(PATH);
        JSONObject jsonObject = string2Json(s);     
    }
}
