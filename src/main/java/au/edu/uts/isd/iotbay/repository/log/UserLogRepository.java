package au.edu.uts.isd.iotbay.repository.log;

import au.edu.uts.isd.iotbay.database.ConnectionProvider;
import au.edu.uts.isd.iotbay.model.log.UserLog;
import au.edu.uts.isd.iotbay.repository.Repository;
import java.util.Optional;

public interface UserLogRepository extends Repository<UserLog> {


    static UserLogRepository create(ConnectionProvider datasource) {
        if (datasource == null) {
            return InMemoryUserLogRepository.concurrent();
        }
        return new PersistentUserLogRepository(datasource);
    }

    Optional<UserLog> findByUserId(Integer userId);
}
