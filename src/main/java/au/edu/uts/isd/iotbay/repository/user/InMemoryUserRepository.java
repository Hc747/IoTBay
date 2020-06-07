package au.edu.uts.isd.iotbay.repository.user;

import au.edu.uts.isd.iotbay.model.user.User;
import org.bson.types.ObjectId;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserRepository implements UserRepository {

    private final Map<String, User> users;

    private InMemoryUserRepository(Map<String, User> users) {
        this.users = Objects.requireNonNull(users);
    }

    @Override
    public User findByUsername(String username) {
        return users.get(username);
    }

    @Override
    public User findById(ObjectId id) {
        return find(user -> user.getId().equals(id)).orElse(null);
    }

    @Override
    public Collection<User> all() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User create(User instance) {
        return users.compute(instance.getUsername(), (String k, User v) -> {
            instance.setId(new ObjectId());
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

    public static InMemoryUserRepository unsynchronised() {
        return new InMemoryUserRepository(new HashMap<>());
    }
}
