package au.edu.uts.wsd.util;

import java.util.UUID;

public final class UUIDGenerator {
    
    public static String generate() {
        return UUID.randomUUID().toString();
    }
    
    private UUIDGenerator() {
        throw new IllegalStateException("Unable to create an instance of the UUIDGenerator class");
    }
    
}
