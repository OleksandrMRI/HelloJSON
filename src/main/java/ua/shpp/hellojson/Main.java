package ua.shpp.hellojson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class Main implements Constants{

    public static void main(String[] args) {
        log.debug("Start class main");
        Properties properties = new Properties();
        if (new File(INSIDE_CONFIG).exists()) {
            String createFile = ", create file " + INSIDE_CONFIG + " in root and reenter application";
            log.debug("Inside if statement INSIDE_CONFIG");
            createInputStream(INSIDE_CONFIG, properties, EXCEPTION_LINE_0, createFile);
        } else if (new File(OUTSIDE_CONFIG).exists()) {
            String createFile = ", create file " + INSIDE_CONFIG + " in root and reenter application";
            log.debug("Inside if statement OUTSIDE_CONFIG");
            createInputStream(OUTSIDE_CONFIG, properties, EXCEPTION_LINE_1, createFile);
        } else {
            log.error("There is no file \"config.properties\" in root catalog");
        }

        String messageHello = "Привіт !!!" + properties.getProperty("username") + "!";
        Message message = new Message(messageHello);
        if(new File(OUTPUT_JSON_FILE).exists()) {
            log.debug("Inside if statement OUTPUT_JSON_FILE)");
            chooseMessage(message, EXCEPTION_LINE_2, OUTPUT_JSON_FILE, properties);
        }else if(new File(INPUT_JSON_FILE).exists()){
            log.debug("Inside else if statement INPUT_JSON_FILE)");
            chooseMessage(message, EXCEPTION_LINE_3, INPUT_JSON_FILE, properties);
        }
    }

    private static void chooseMessage(Message message, int line, String path, Properties properties) {
        ObjectMapper mapper;
        if(System.getProperty("format").equals("JSON")) {
            mapper = new ObjectMapper();
        } else {
            mapper = new XmlMapper();
        }
        writeMessage(message, mapper, line, path);
    }

    private static void writeMessage(Message message, ObjectMapper objectMapper, int line,String path) {
        try {
            log.info(objectMapper.writeValueAsString(message));
            objectMapper.writeValue(new File(path), message);
        } catch (JsonProcessingException e) {
            log.error("There is no JSON structure", e);
        } catch (IOException e) {
            log.error("Line {}, FileNotFoundException, create file {} in root and reenter application",line, path, e);
        }
    }

    private static void createInputStream(String insideConfig, Properties properties, int line, String createFile) {
        try (InputStream im = new FileInputStream(insideConfig);
             InputStreamReader isr = new InputStreamReader(im, StandardCharsets.UTF_8);
             BufferedReader bf = new BufferedReader(isr)) {
            log.info("Program creates input stream");
            properties.load(bf);
        } catch (IOException e) {
            log.error("Line {}, FileNotFoundException{}", line, createFile, e);
        }
    }
}
