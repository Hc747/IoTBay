package au.edu.uts.isd.iotbay.action;

import au.edu.uts.isd.iotbay.action.impl.LoginAction;
import au.edu.uts.isd.iotbay.action.impl.LogoutAction;
import au.edu.uts.isd.iotbay.action.impl.RegisterAction;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 *
 * @author Harrison
 */
public class ActionRegistry {
    
    //TODO: make non-static
    //TODO: implement service loader
    private static final Map<String, Supplier<Action>> ACTIONS;
    
    static {
        Map<String, Supplier<Action>> actions = new HashMap<>();
        
        actions.put("register", RegisterAction::new);
        actions.put("login", LoginAction::new);
        actions.put("logout", LogoutAction::new);
        
        
        ACTIONS = actions;
    }
    
    public static Action get(String key) {
        Supplier<Action> supplier = ACTIONS.get(key);
        if (supplier == null) {
            return null;
        }
        return supplier.get();
    }
    
}
