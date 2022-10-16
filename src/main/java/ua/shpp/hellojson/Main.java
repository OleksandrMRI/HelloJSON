package ua.shpp.hellojson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static final String OUTSIDE_CONFIG = "config.properties";
    private static final String INSIDE_CONFIG = "conf/config.properties";
    private static final String OUTPUT_JSON_FILE = "message.json";
    private static final String INPUT_JSON_FILE = "conf/message.json";

    public static void main(String[] args) {
        log.debug("Start class main");
        Properties properties = new Properties();
        if (new File(INSIDE_CONFIG).exists()) {
            String createFile = ", create file " + INSIDE_CONFIG + " in root and reenter application";
            log.debug("Inside if statement INSIDE_CONFIG, line 18");
            getInputStream(INSIDE_CONFIG, properties, 20, createFile);
        } else if (new File(OUTSIDE_CONFIG).exists()) {
            String createFile = ", create file " + INSIDE_CONFIG + " in root and reenter application";
            log.debug("Inside if statement OUTSIDE_CONFIG, line 27");
            getInputStream(OUTSIDE_CONFIG, properties, 23, createFile);
        } else {
            log.error("There is no file \"config.properties\" in root catalog");
        }

        String messageHello = "Привіт " + properties.getProperty("username") + "!";
        Message message = new Message(messageHello);
        File file = new File(OUTPUT_JSON_FILE);
        if(file.exists()) {
            log.debug("Inside if statement, line 37)");
            writeMessage(message, 35, file, OUTPUT_JSON_FILE, properties);
        }else if(new File(INPUT_JSON_FILE).exists()){
            log.debug("Inside else if statement, line 40)");
            writeMessage(message, 38, new File(INPUT_JSON_FILE),INPUT_JSON_FILE, properties);
        }
    }

    private static void writeMessage(Message message, int line, File file, String path, Properties properties) {
        try {
            if(properties.getProperty("outputformat").equals("JSON")) {
                ObjectMapper objectMapper = new ObjectMapper();
                log.info(objectMapper.writeValueAsString(message));
                objectMapper.writeValue(file, message);
            } else {
                XmlMapper xmlMapper = new XmlMapper();
                log.info(xmlMapper.writeValueAsString(message));
                xmlMapper.writeValue(file, message);
            }
        } catch (JsonProcessingException e) {
            log.error("There is no JSON structure, line 41", e);
        } catch (IOException e) {
            log.error("Line {}, FileNotFoundException, create file {} in root and reenter application",line, path, e);
        }
    }

    private static void getInputStream(String insideConfig, Properties properties, int line, String createFile) {
        try (InputStream im = new FileInputStream(insideConfig);
             InputStreamReader isr = new InputStreamReader(im, StandardCharsets.UTF_8);
             BufferedReader bf = new BufferedReader(isr)) {
            properties.load(bf);
        } catch (IOException e) {
            log.error("Line {}, FileNotFoundException{}", line, createFile, e);
        }
    }
}