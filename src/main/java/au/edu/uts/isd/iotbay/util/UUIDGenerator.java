package au.edu.uts.isd.iotbay.util;

import java.util.UUID;

public final class UUIDGenerator {
    
    public static UUID generate() {
        return UUID.randomUUID();
    }
    
    private UUIDGenerator() {
        throw new IllegalStateException("Unable to create an instance of the UUIDGenerator class");
    }
}
