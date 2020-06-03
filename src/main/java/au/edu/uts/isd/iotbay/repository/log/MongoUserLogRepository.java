package au.edu.uts.isd.iotbay.repository.log;

import au.edu.uts.isd.iotbay.model.log.UserLog;
import au.edu.uts.isd.iotbay.model.user.User;
import au.edu.uts.isd.iotbay.persistence.mongo.MongoDatabaseProvider;
import au.edu.uts.isd.iotbay.repository.MongoRepository;
import com.mongodb.client.model.Filters;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.lt;

public class MongoUserLogRepository extends MongoRepository<UserLog> implements UserLogRepository {

    protected MongoUserLogRepository(MongoDatabaseProvider datasource) {
        super(datasource, UserLog.class);
    }

    @Override
    public Collection<UserLog> findByUser(User user) {
        //TODO: user.id
        return findAll(eq("user", user));
    }

    @Override
    public Collection<UserLog> findByUserBeforeDate(User user, Date date) {
        //TODO: user.id
        //TODO: timestamp
        final Timestamp timestamp = Timestamp.from(date.toInstant());
        return findAll(Filters.and(eq("user", user), lt("timestamp", timestamp)));
    }
}
