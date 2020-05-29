package au.edu.uts.isd.iotbay.repository.log;

import au.edu.uts.isd.iotbay.model.log.UserLog;
import au.edu.uts.isd.iotbay.model.product.Product;
import au.edu.uts.isd.iotbay.model.user.User;
import au.edu.uts.isd.iotbay.repository.user.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryUserLogRepository implements UserLogRepository {

    private static final AtomicInteger SEQUENCE = new AtomicInteger(1);

    private final Map<Integer, UserLog> userlogs;

    private InMemoryUserLogRepository(Map<Integer, UserLog> userlogs) {
        this.userlogs = Objects.requireNonNull(userlogs);
    }

    @Override
    public Optional<UserLog> findByUserId(Integer userId) {
        return Optional.ofNullable(userlogs.get(userId));
    }

    @Override
    public Collection<UserLog> all() {
        return new ArrayList<>(userlogs.values());
    }

    @Override
    public UserLog create(UserLog instance) {
//        final Integer id = SEQUENCE.getAndIncrement();
        final Integer userId = 0; //CHANGE THIS
        return userlogs.compute(userId, (k, v) -> {
            instance.setId(userId);
            return instance;
        });
    }

    @Override
    public UserLog update(UserLog instance) {
        return null;
    }

    @Override
    public UserLog delete(UserLog instance) {
        return null;
    }

    public static au.edu.uts.isd.iotbay.repository.log.InMemoryUserLogRepository concurrent() {
        return new au.edu.uts.isd.iotbay.repository.log.InMemoryUserLogRepository(new ConcurrentHashMap<>());
    }

    public static au.edu.uts.isd.iotbay.repository.log.InMemoryUserLogRepository synchronised() {
        return new au.edu.uts.isd.iotbay.repository.log.InMemoryUserLogRepository(Collections.synchronizedMap(new HashMap<>()));
    }

    public static au.edu.uts.isd.iotbay.repository.log.InMemoryUserLogRepository unsynchronised() {
        return new au.edu.uts.isd.iotbay.repository.log.InMemoryUserLogRepository(new HashMap<>());
    }
}

