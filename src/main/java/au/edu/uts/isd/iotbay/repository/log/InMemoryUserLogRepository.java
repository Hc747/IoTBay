package au.edu.uts.isd.iotbay.repository.log;

import au.edu.uts.isd.iotbay.model.log.UserLog;
import au.edu.uts.isd.iotbay.model.user.User;
import org.bson.types.ObjectId;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserLogRepository implements UserLogRepository {

    private final Map<ObjectId, UserLog> userlogs;

    private InMemoryUserLogRepository(Map<ObjectId, UserLog> userlogs) {
        this.userlogs = Objects.requireNonNull(userlogs);
    }

    @Override
    public Collection<UserLog> findByUser(User user) {
        return findAll(log -> log.getUser().equals(user));
    }

    @Override
    public Collection<UserLog> findByUserBeforeDate(User user, Date date) {
        return null;
    }

    @Override
    public Collection<UserLog> all() {
        return new ArrayList<>(userlogs.values());
    }

    @Override
    public UserLog create(UserLog instance) {
        final ObjectId id = new ObjectId();
        return userlogs.compute(id, (k, v) -> {
            instance.setId(id);
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

    public static InMemoryUserLogRepository concurrent() {
        return new InMemoryUserLogRepository(new ConcurrentHashMap<>());
    }

    public static InMemoryUserLogRepository synchronised() {
        return new InMemoryUserLogRepository(Collections.synchronizedMap(new HashMap<>()));
    }

    public static InMemoryUserLogRepository unsynchronised() {
        return new InMemoryUserLogRepository(new HashMap<>());
    }
}

