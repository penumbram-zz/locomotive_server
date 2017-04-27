package app.rest.util;

/**
 * Created by tolgacaner on 27/04/2017.
 */
public class Utility {
    public static String serviceFromLocalEndpoint(String endpoint) {
        return "http://localhost:8080" + endpoint;
    }
}
