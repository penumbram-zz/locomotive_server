package app.rest.util;

import app.rest.socket.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by tolgacaner on 27/04/2017.
 */
public class Utility {
    public static String serviceFromLocalEndpoint(String endpoint) {
        return "http://localhost:8080" + endpoint;
    }

    public static <T> T stringToJSONObject(String string, Class<T> valueType) {
        ObjectMapper mapper = new ObjectMapper();
        T t = null;
        try {
            t = mapper.readValue(string, valueType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }
}
