package ua.shpp.hellojson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Constants {
    Logger log = LoggerFactory.getLogger(Main.class);
    String OUTSIDE_CONFIG = "config.properties";
    String INSIDE_CONFIG = "conf/config.properties";
    String OUTPUT_JSON_FILE = "message.json";
    String INPUT_JSON_FILE = "conf/message.json";
    int EXCEPTION_LINE_0 = 16;
    int EXCEPTION_LINE_1 = 20;
    int EXCEPTION_LINE_2 = 30;
    int EXCEPTION_LINE_3 = 33;
}
