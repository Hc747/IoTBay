package au.edu.uts.isd.iotbay.repository.user;

import au.edu.uts.isd.iotbay.model.user.User;
import au.edu.uts.isd.iotbay.persistence.mongo.MongoDatabaseProvider;
import au.edu.uts.isd.iotbay.repository.Repository;

public interface UserRepository extends Repository<User> {

    User findByUsername(String username);
    
    static UserRepository create(MongoDatabaseProvider datasource) {
        if (datasource == null) {
            return InMemoryUserRepository.concurrent();
        }
        return new MongoUserRepository(datasource);
    }
}
