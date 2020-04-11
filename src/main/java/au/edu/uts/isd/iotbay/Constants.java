package au.edu.uts.isd.iotbay;

public final class Constants {
    
    public static final String APPLICATION_NAME = "IoTBay";
    
    public static final String APPLICATION_DESCRIPTION = "IoTBay is an online store for all your IoT needs!";
    
    public static final String BASE_URL = "http://localhost:8080/iotbay/";
    
    private Constants() {
        throw new IllegalStateException("Unable to create an instance of the Constants class");
    }
}
