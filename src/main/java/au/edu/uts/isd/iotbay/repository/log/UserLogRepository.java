package au.edu.uts.isd.iotbay.repository.log;

import au.edu.uts.isd.iotbay.database.ConnectionProvider;
import au.edu.uts.isd.iotbay.model.log.UserLog;
import au.edu.uts.isd.iotbay.model.user.User;
import au.edu.uts.isd.iotbay.repository.Repository;

import java.util.Collection;

public interface UserLogRepository extends Repository<UserLog> {

    Collection<UserLog> findByUser(User user);

    static UserLogRepository create(ConnectionProvider datasource) {
        if (datasource == null) {
            return InMemoryUserLogRepository.concurrent();
        }
        return new PersistentUserLogRepository(datasource);
    }
}
