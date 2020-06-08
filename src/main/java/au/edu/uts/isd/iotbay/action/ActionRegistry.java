package au.edu.uts.isd.iotbay.action;

import java.util.function.Supplier;

/**
 * Provides a mechanism for dynamically registering and de-registering {@code Action}s invocable by the Application.
 */
public interface ActionRegistry {

    boolean register(String key, Supplier<Action> action);

    boolean deregister(String key);

    /**
     * @return
     * The new instance of the {@code Action}, or null, associated with the parameterised {@code key}.
     */
    Action get(String key);
}