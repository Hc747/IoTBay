package au.edu.uts.isd.iotbay.repository.log;

import au.edu.uts.isd.iotbay.model.log.UserLog;
import au.edu.uts.isd.iotbay.model.user.User;
import au.edu.uts.isd.iotbay.persistence.mongo.MongoDatabaseProvider;
import au.edu.uts.isd.iotbay.repository.Repository;

import java.util.Collection;
import java.util.Date;

public interface UserLogRepository extends Repository<UserLog> {

    Collection<UserLog> findByUser(User user);

    Collection<UserLog> findByUserBeforeDate(User user, Date date);

    static UserLogRepository create(MongoDatabaseProvider datasource) {
        if (datasource == null) {
            return new InMemoryUserLogRepository();
        }
        return new MongoUserLogRepository(datasource);
    }
}
