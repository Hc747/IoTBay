package au.edu.uts.isd.iotbay.repository.user;

import au.edu.uts.isd.iotbay.model.user.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserRepository implements UserRepository {

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
    public User save(User instance) {
        return users.compute(instance.getUsername(), (String k, User v) -> instance);
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
