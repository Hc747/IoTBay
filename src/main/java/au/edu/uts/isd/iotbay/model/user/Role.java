package au.edu.uts.isd.iotbay.model.user;

public enum Role {
    UNKNOWN,
    USER,
    STAFF,
    ADMINISTRATOR
    ;

    public boolean isAuthenticated() {
        return this != UNKNOWN;
    }

    public boolean isStaff() {
        return this == STAFF || this == ADMINISTRATOR;
    }

    public static Role findByName(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }
}
