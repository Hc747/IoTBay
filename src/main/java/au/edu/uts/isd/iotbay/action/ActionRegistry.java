package au.edu.uts.isd.iotbay.action;

import java.util.function.Supplier;

public interface ActionRegistry {

    boolean register(String key, Supplier<Action> action);

    boolean deregister(String key);

    Action get(String key);
}