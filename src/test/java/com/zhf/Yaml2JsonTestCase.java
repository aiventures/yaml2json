package com.zhf;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;

/**
 * Unit test for simple App.
 */
class Yaml2JsonTest {

    private static final Logger logger = LogManager.getLogger(Yaml2JsonTest.class);    
    private static Path wPath;
    private static final String SAMPLE_YML = "sample.yml";
    private static final String SAMPLE_ERROR_YML = "sample_error.yml";
    private static final String SAMPLE_NONEXISTENT_YML = "sample_error_xyz.yml";

    @BeforeAll
    static void beforeAll() {
        String rootPath = Paths.get("").toAbsolutePath().toString();
        // get paths to test files
        wPath = Paths.get(rootPath,"src","test","resources").toAbsolutePath();        
        logger.info("Setting Working Path to {}, is Directory: {}",wPath.toString(),Files.isDirectory(wPath));
    }

    @Test
    void TEST_read_correct_yaml_file_without_input_params() {
        Yaml2Json.error_code = Yaml2Json.STATUS_NO_ERROR;
        assertTrue(Files.isDirectory(wPath));
        Path filePath = Paths.get(wPath.toString(),SAMPLE_YML).toAbsolutePath();
        boolean isFile = Files.isRegularFile(filePath);
        logger.info("\nUsing File {}, is File: {}",filePath.toString(),isFile);
        assertTrue(isFile);
        Yaml2Json cut = new Yaml2Json();
        String[] args = new String[0];
        cut.main(args);
        assertEquals(Yaml2Json.error_code,Yaml2Json.STATUS_NO_ERROR);
    }

    @Test
    void TEST_read_correct_yaml_file_with_input_params() {
        Yaml2Json.error_code = Yaml2Json.STATUS_NO_ERROR;
        assertTrue(Files.isDirectory(wPath));
        Path filePath = Paths.get(wPath.toString(),SAMPLE_YML).toAbsolutePath(); 
        boolean isFile = Files.isRegularFile(filePath);
        logger.info("\nUsing File {}, is File: {}",filePath.toString(),isFile);
        assertTrue(isFile);
        String args[]  = new String[] {filePath.toString()};
        Yaml2Json cut = new Yaml2Json();
        cut.main(args);
        assertEquals(Yaml2Json.error_code,Yaml2Json.STATUS_NO_ERROR);
    }    

    @Test
    void TEST_read_invalid_yaml_file_with_input_params() {
        Yaml2Json.error_code = Yaml2Json.STATUS_NO_ERROR;
        assertTrue(Files.isDirectory(wPath));
        Path filePath = Paths.get(wPath.toString(),SAMPLE_ERROR_YML).toAbsolutePath(); 
        boolean isFile = Files.isRegularFile(filePath);
        logger.info("\nUsing File {}, is File: {}",filePath.toString(),isFile);
        assertTrue(isFile);
        String args[]  = new String[] {filePath.toString()};
        Yaml2Json cut = new Yaml2Json();
        cut.main(args);
        assertEquals(Yaml2Json.error_code,Yaml2Json.STATUS_ERROR);
    }      

    @Test
    void TEST_read_non_existent_yaml_file_with_input_params() {
        Yaml2Json.error_code = Yaml2Json.STATUS_NO_ERROR;
        assertTrue(Files.isDirectory(wPath));
        Path filePath = Paths.get(wPath.toString(),SAMPLE_NONEXISTENT_YML).toAbsolutePath(); 
        boolean isFile = Files.isRegularFile(filePath);
        logger.info("\nUsing File {}, is File: {}",filePath.toString(),isFile);
        assertFalse(isFile);
        String args[]  = new String[] {filePath.toString()};
        Yaml2Json cut = new Yaml2Json();
        cut.main(args);
        assertEquals(Yaml2Json.error_code,Yaml2Json.STATUS_ERROR);
    }        
}
