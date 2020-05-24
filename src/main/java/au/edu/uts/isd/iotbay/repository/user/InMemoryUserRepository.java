package au.edu.uts.isd.iotbay.repository.user;

import au.edu.uts.isd.iotbay.model.user.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
    public Collection<User> findAll(Predicate<User> criteria) {
        return users.values().stream().filter(criteria).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Optional<User> find(Predicate<User> criteria) {
        for (User user : users.values()) {
            if (criteria.test(user)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
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
