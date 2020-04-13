package au.edu.uts.isd.iotbay;

import au.edu.uts.isd.iotbay.repository.user.UserRepository;
import lombok.Getter;

import javax.servlet.ServletContext;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.Supplier;

@Getter
public final class IoTBayApplicationContext implements Serializable {
    
    private static final String CONTEXT_KEY = "application-context";
    
    private final UserRepository users;
    
    IoTBayApplicationContext(UserRepository users) {
        this.users = Objects.requireNonNull(users);
    }
    
    //TODO: product repository
    //TODO: order repository
    //TODO: other repositories

    public static IoTBayApplicationContext getInstance(ServletContext application, Supplier<IoTBayApplicationContext> supplier) {
        return getInstance(application, CONTEXT_KEY, supplier);
    }

    public static IoTBayApplicationContext getInstance(ServletContext application) {
        return getInstance(application, IoTBayApplicationContextHolder::holder);
    }

    private static <T> T getInstance(ServletContext application, String key, Supplier<T> supplier) {
        T result = (T) application.getAttribute(key);
        if (result == null) {
            synchronized (application) {
                result = (T) application.getAttribute(key);
                if (result == null) {
                    result = supplier.get();
                    application.setAttribute(key, result);
                }
            }
        }
        return result;
    }

    private static final class IoTBayApplicationContextHolder {
        private static final IoTBayApplicationContext DEFAULT = new IoTBayApplicationContext(
            UserRepository.create()
        );
        
        private static IoTBayApplicationContext holder() { return DEFAULT; }
    }
}
