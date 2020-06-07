package au.edu.uts.isd.iotbay.repository.user;

import au.edu.uts.isd.iotbay.model.user.User;
import au.edu.uts.isd.iotbay.persistence.mongo.MongoDatabaseProvider;
import au.edu.uts.isd.iotbay.repository.MongoRepository;

import static com.mongodb.client.model.Filters.eq;

public class MongoUserRepository extends MongoRepository<User> implements UserRepository {

    public MongoUserRepository(MongoDatabaseProvider datasource) {
        super(datasource, User.class);
    }

    @Override
    public User findByUsername(String username) {
        return find(eq("username", username));
    }
}
