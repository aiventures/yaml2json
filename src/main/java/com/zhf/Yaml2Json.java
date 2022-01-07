package com.zhf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
    simple YAML to JSON Converter 
    credit goes to https://github.com/stleary/JSON-java
 */
public final class Yaml2Json {
    private static final Logger logger = LogManager.getLogger(Yaml2Json.class);

    private Yaml2Json() {
    }    

    public static void main(String[] args) {
        int error_code = 0;
        logger.debug("Entering main(), #of args {}",args.length);
        Yaml2JsonHelper yaml2JsonHelper = new Yaml2JsonHelper(args);        
        error_code = yaml2JsonHelper.process();
        logger.traceExit("Processing finished with code: {}", error_code);
    }
}
