package au.edu.uts.isd.iotbay;

import au.edu.uts.isd.iotbay.repository.user.UserRepository;
import lombok.Getter;

import javax.servlet.ServletContext;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.Supplier;

@Getter
public final class IoTBayApplicationContext implements Serializable {
    
    private static final String CONTEXT_KEY = "IoTBay";
    
    private final UserRepository users;
    
    IoTBayApplicationContext(UserRepository users) {
        this.users = Objects.requireNonNull(users);
    }
    
    //TODO: product repository
    //TODO: order repository
    //TODO: other repositories

    public static IoTBayApplicationContext getInstance(ServletContext application, Supplier<IoTBayApplicationContext> supplier) {
        IoTBayApplicationContext ctx = (IoTBayApplicationContext) application.getAttribute(CONTEXT_KEY);
        if (ctx == null) { //TODO: ensure double checked locking is okay
            synchronized (application) {
                ctx = (IoTBayApplicationContext) application.getAttribute(CONTEXT_KEY);
                if (ctx == null) {
                    ctx = supplier.get();
                    application.setAttribute(CONTEXT_KEY, ctx);
                }
            }
        }
        return ctx;
    }
    
    public static IoTBayApplicationContext getInstance(ServletContext application) {
        return getInstance(application, IoTBayApplicationContextHolder::holder);
    }
    
    private static final class IoTBayApplicationContextHolder {
        private static final IoTBayApplicationContext DEFAULT = new IoTBayApplicationContext(
            UserRepository.create()
        );
        
        private static IoTBayApplicationContext holder() { return DEFAULT; }
    }

}
