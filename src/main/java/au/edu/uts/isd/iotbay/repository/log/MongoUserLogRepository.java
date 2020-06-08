package au.edu.uts.isd.iotbay.repository.log;

import au.edu.uts.isd.iotbay.model.log.UserLog;
import au.edu.uts.isd.iotbay.model.user.User;
import au.edu.uts.isd.iotbay.persistence.mongo.MongoDatabaseProvider;
import au.edu.uts.isd.iotbay.repository.MongoRepository;
import com.mongodb.client.model.Filters;

import java.time.LocalDate;
import java.util.Collection;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.lt;

public class MongoUserLogRepository extends MongoRepository<UserLog> implements UserLogRepository {

    protected MongoUserLogRepository(MongoDatabaseProvider datasource) {
        super(datasource, UserLog.class);
    }

    @Override
    public Collection<UserLog> findByUser(User user) {
        //TODO: user.id
        return findAll(eq("user._id", user.getId()));
    }

    //retrieves the user logs by the user and inputted date
    @Override
    public Collection<UserLog> findByUserBeforeDate(User user, LocalDate date) {
        return findAll(Filters.and(eq("user._id", user.getId()), lt("date", date)));
    }
}
