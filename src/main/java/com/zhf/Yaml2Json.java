package com.zhf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
    simple YAML to JSON Converter 
    credit goes to https://github.com/stleary/JSON-java
 */
public final class Yaml2Json {
    public final static int STATUS_ERROR = -1;
    public final static int STATUS_NO_ERROR = 0;
    private static final Logger logger = LogManager.getLogger(Yaml2Json.class);
    public static int error_code = 0;

    public Yaml2Json() {
    }    

    public static void main(String[] args) {
        error_code = 0;
        logger.debug("Entering main(), #of args {}",args.length);
        Yaml2JsonHelper yaml2JsonHelper = new Yaml2JsonHelper(args);        
        error_code = yaml2JsonHelper.process();
        logger.traceExit("Processing finished with code: {}", error_code);
    }
}
