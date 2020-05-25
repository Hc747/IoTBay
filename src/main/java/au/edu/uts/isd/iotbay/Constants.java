package au.edu.uts.isd.iotbay;

import java.util.StringJoiner;

public final class Constants {
    
    public static final String APPLICATION_NAME = "IoTBay";
    
    public static final String APPLICATION_DESCRIPTION = "IoTBay is an online store for all your IoT needs!";
    
    private static final String BASE_URL = "http://localhost:8080/iotbay/";

    public static String path(boolean directory, String... components) {
        if (components == null || components.length == 0) {
            return BASE_URL;
        }
        final StringJoiner joiner = new StringJoiner("/");
        for (String component : components) {
            joiner.add(component);
        }
        return BASE_URL + joiner.toString() + (directory ? "/" : "");
    }
    
    private Constants() {
        throw new IllegalStateException("Unable to create an instance of the Constants class");
    }
}
