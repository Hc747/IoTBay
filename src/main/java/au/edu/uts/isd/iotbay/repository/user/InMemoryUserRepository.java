package au.edu.uts.isd.iotbay.repository.user;

import au.edu.uts.isd.iotbay.model.user.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryUserRepository implements UserRepository {

    private static final AtomicInteger SEQUENCE = new AtomicInteger(1);

    private final Map<String, User> users;

    private InMemoryUserRepository(Map<String, User> users) {
        this.users = Objects.requireNonNull(users);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(users.get(username));
    }

    @Override
    public Collection<User> all() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User create(User instance) {
        return users.compute(instance.getUsername(), (String k, User v) -> {
            instance.setId(SEQUENCE.getAndIncrement());
            return instance;
        });
    }

    @Override
    public User update(User instance) {
        users.put(instance.getUsername(), instance);
        return instance;
    }

    @Override
    public User delete(User instance) {
        return users.remove(instance.getUsername(), instance) ? instance : null;
    }

    public static InMemoryUserRepository concurrent() {
        return new InMemoryUserRepository(new ConcurrentHashMap<>());
    }

    public static InMemoryUserRepository synchronised() {
        return new InMemoryUserRepository(Collections.synchronizedMap(new HashMap<>()));
    }

    public static InMemoryUserRepository unsafe() {
        return new InMemoryUserRepository(new HashMap<>());
    }
}
