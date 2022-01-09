package com.zhf;
import java.util.Map;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.scanner.ScannerException;

/**
    simple YAML to JSON Converter 
    credit goes to https://github.com/stleary/JSON-java
 */
public final class Yaml2JsonHelper {
    private static final Logger logger = LogManager.getLogger(Yaml2JsonHelper.class);
    private static String ymlPath = "";
    private static String jsonPath = "";
    private int err_code = 0;

    public Yaml2JsonHelper(String[] args) {
      handleArgs(args);
    }

    // does the processing, returns error code
    public int process() {
      String content = this.readYml(ymlPath);
      if (content==null) {
        return -1;
      }
      
      JSONObject jsonObject = string2Json(content);      
      if (jsonObject==null) {
        return -1;        
      }

      this.err_code = this.saveJson(jsonObject);
      return this.err_code;
    }

    // recursively transform elements to json
    private static Object convertToJson(Object o) throws JSONException {
        logger.trace("convert to JSON Object:\n  {}", o.toString());

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

    private int saveJson(JSONObject jsonObject) 
    {
      logger.debug("saving YML as JSON to [{}]", this.jsonPath);
      try {
          FileWriter f = new FileWriter(this.jsonPath);          
          f.write(jsonObject.toString(4));
          f.close();
          return 0;
      } catch (IOException e) {
          logger.fatal("Error writing file:", e);
          return -1;
      } 
    }

    private String readYml(String filePath) 
    {
        String content = "";
        try {
            content = new String ( Files.readAllBytes( Paths.get(filePath) ) );
            logger.trace("File ({}), contents:\n{}",filePath,content);
        } 
        catch (IOException e) {
            logger.error("Error reading file:{}, exception {}",filePath,e);
            this.err_code = -1;
        } 
        return content;
    }    
    
    private static JSONObject string2Json(String s) 
    {   
        logger.traceEntry();
        Yaml yaml= new Yaml();
        try {
          Map<String,Object> map=(Map<String, Object>) yaml.load(s); 
          logger.trace("Mapped string {} \n", map);
          JSONObject jsonObject = (JSONObject) convertToJson(map);
          logger.debug("JSON Object {} \n", jsonObject);
          return jsonObject;                    
        } catch (ScannerException e) {
          logger.error("Error mapping yml to map {}",e.getMessage());
          return null;
        } 
    } 

    // handle input Params, returns error code
    private void handleArgs(String[] args) {

      // get sample yaml from work folder if no data is submitted
      int num_args = args.length;
      Path ymlPathO;
      Path parentPath=null;

      if (num_args>0) {
        ymlPathO =  Paths.get(args[0]).toAbsolutePath();
      } else {
        String workPath = Paths.get("").toAbsolutePath().toString();
        ymlPathO = Paths.get(workPath,"sample.yml").toAbsolutePath();
      }

      boolean fileExists = Files.exists(ymlPathO);
      logger.debug("1st Argument (or default): \nPath {}, exists: {}",ymlPathO.toString(),fileExists);

      if (fileExists) {
        parentPath = ymlPathO.getParent();
        ymlPath = ymlPathO.toString();        
      } else {
        logger.error("Couldn't find YAML file {}", ymlPathO.toString());
        this.err_code = -1;
        return;
      }

      // 2nd argument: json file name,       
      // use name of yml file
      String jsonFilename = "";
      if (num_args<=1) {
        jsonFilename = ymlPathO.getFileName().toString();
        jsonFilename = jsonFilename.substring(0, jsonFilename.length() - 3)+"json";
      } else {
        jsonFilename = args[1];
      }

      jsonPath = Paths.get(parentPath.toString(),jsonFilename).toString();
      this.err_code = 0;
      return;
    }

}
