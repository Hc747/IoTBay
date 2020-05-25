package au.edu.uts.isd.iotbay;

import au.edu.uts.isd.iotbay.database.ConnectionProvider;
import au.edu.uts.isd.iotbay.repository.payment.PaymentMethodRepository;
import au.edu.uts.isd.iotbay.repository.user.UserRepository;
import lombok.Getter;

import javax.annotation.PreDestroy;
import javax.servlet.ServletContext;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.Supplier;

import static au.edu.uts.isd.iotbay.database.ConnectionProviderFactory.hikari;

@Getter
public final class IoTBayApplicationContext implements Serializable, AutoCloseable {
    
    private static final String CONTEXT_KEY = "application-context";
    private static final String PROPERTIES_PATH = "WEB-INF/database.properties";

    private final ConnectionProvider datasource;
    private final UserRepository users;
    private final PaymentMethodRepository payments;
    
    IoTBayApplicationContext(ConnectionProvider datasource, UserRepository users, PaymentMethodRepository payments) {
        this.datasource = Objects.requireNonNull(datasource);
        this.users = Objects.requireNonNull(users);
        this.payments = Objects.requireNonNull(payments);
    }

    IoTBayApplicationContext(ConnectionProvider datasource) {
        this(datasource, UserRepository.create(datasource), PaymentMethodRepository.create(datasource));
    }
    
    //TODO: product repository
    //TODO: order repository
    //TODO: other repositories

    public static IoTBayApplicationContext getInstance(ServletContext application, Supplier<IoTBayApplicationContext> supplier) {
        return getInstance(application, CONTEXT_KEY, supplier);
    }

    public static IoTBayApplicationContext getInstance(ServletContext application) {
        return getInstance(application, () -> {
            final ConnectionProvider datasource;
            if (Constants.PERSISTENCE_ENABLED) {
                final String properties = application.getRealPath(PROPERTIES_PATH);
                datasource = hikari(properties);
            } else {
                datasource = null;
            }
            return new IoTBayApplicationContext(datasource);
        });
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

    @Override
    @PreDestroy
    public void close() {
        try {
            datasource.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
