package au.edu.uts.isd.iotbay.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class InMemoryActionRegistry implements ActionRegistry {

    private final Map<String, Supplier<Action>> actions;

    private InMemoryActionRegistry(Map<String, Supplier<Action>> actions) {
        this.actions = Objects.requireNonNull(actions);
    }

    @Override
    public boolean register(String key, Supplier<Action> action) {
        Supplier<Action> computed = actions.compute(key, (k, v) -> v == null ? action : v);
        return computed == action;
    }

    @Override
    public boolean deregister(String key) {
        return actions.remove(key) != null;
    }

    @Override
    public Action get(String key) {
        Supplier<Action> action = actions.get(key);
        return action == null ? null : action.get();
    }

    public static InMemoryActionRegistry concurrent() {
        return new InMemoryActionRegistry(new ConcurrentHashMap<>());
    }

    public static InMemoryActionRegistry synchronised() {
        return new InMemoryActionRegistry(Collections.synchronizedMap(new HashMap<>()));
    }

    public static InMemoryActionRegistry unsynchronised() {
        return new InMemoryActionRegistry(new HashMap<>());
    }
}
