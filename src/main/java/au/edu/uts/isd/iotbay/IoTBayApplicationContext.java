package au.edu.uts.isd.iotbay;

import au.edu.uts.isd.iotbay.action.ActionProcessor;
import au.edu.uts.isd.iotbay.action.ActionRegistry;
import au.edu.uts.isd.iotbay.action.InMemoryActionRegistry;
import au.edu.uts.isd.iotbay.action.impl.*;
import au.edu.uts.isd.iotbay.database.ConnectionProvider;
import au.edu.uts.isd.iotbay.model.log.UserLog;
import au.edu.uts.isd.iotbay.model.user.User;
import au.edu.uts.isd.iotbay.repository.category.CategoryRepository;
import au.edu.uts.isd.iotbay.repository.log.UserLogRepository;
import au.edu.uts.isd.iotbay.repository.payment.PaymentMethodRepository;
import au.edu.uts.isd.iotbay.repository.product.ProductRepository;
import au.edu.uts.isd.iotbay.repository.user.UserRepository;
import lombok.Getter;

import javax.annotation.PreDestroy;
import javax.servlet.ServletContext;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.function.Supplier;

import static au.edu.uts.isd.iotbay.database.ConnectionProviderFactory.hikari;

@Getter
public final class IoTBayApplicationContext implements Serializable, AutoCloseable {
    
    private static final String CONTEXT_KEY = "application-context";
    private static final String PROPERTIES_PATH = "WEB-INF/database.properties";

    private final ConnectionProvider datasource;
    private final ActionProcessor processor;
    private final UserRepository users;
    private final PaymentMethodRepository payments;
    private final ProductRepository products;
    private final CategoryRepository categories;
    private final UserLogRepository userLogs;
    
    IoTBayApplicationContext(ConnectionProvider datasource, ActionProcessor processor, UserRepository users, PaymentMethodRepository payments, ProductRepository products, CategoryRepository categories, UserLogRepository userLogs) {
        this.datasource = datasource;
        this.processor = Objects.requireNonNull(processor);
        this.users = Objects.requireNonNull(users);
        this.payments = Objects.requireNonNull(payments);
        this.products = Objects.requireNonNull(products);
        this.categories = Objects.requireNonNull(categories);
        this.userLogs = Objects.requireNonNull(userLogs);
    }

    IoTBayApplicationContext(ConnectionProvider datasource, ActionProcessor processor) {
        this(datasource, processor, UserRepository.create(datasource), PaymentMethodRepository.create(datasource), ProductRepository.create(datasource), CategoryRepository.create(datasource), UserLogRepository.create(datasource));
    }

    public void log(User user, String type) {
        userLogs.create(new UserLog(null, user, type, Timestamp.from(Instant.now())));
    }
    
    public static IoTBayApplicationContext getInstance(ServletContext application, Supplier<IoTBayApplicationContext> supplier) {
        return getInstance(application, CONTEXT_KEY, supplier);
    }

    public static IoTBayApplicationContext getInstance(ServletContext application) {
        return getInstance(application, () -> {
            final ActionProcessor processor = new ActionProcessor(createRegistry());
            final ConnectionProvider datasource;
            if (Constants.PERSISTENCE_ENABLED) {
                final String properties = application.getRealPath(PROPERTIES_PATH);
                datasource = hikari(properties);
            } else {
                datasource = null;
            }
            return new IoTBayApplicationContext(datasource, processor);
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

    private static ActionRegistry createRegistry() {
        final ActionRegistry registry = InMemoryActionRegistry.concurrent();

        registry.register("login", LoginAction::new);
        registry.register("register", RegisterAction::new);
        registry.register("logout", LogoutAction::new);
        registry.register("user", UserAction::new);
        registry.register("product", ProductAction::new);
        registry.register("payment", PaymentAction::new);

        return registry;
    }

    @Override
    @PreDestroy
    public void close() {
        try {
            if (datasource != null) {
                datasource.close();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
