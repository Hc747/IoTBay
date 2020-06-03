package au.edu.uts.isd.iotbay.repository.user;

import au.edu.uts.isd.iotbay.model.user.User;
import au.edu.uts.isd.iotbay.persistence.mongo.MongoDatabaseProvider;
import au.edu.uts.isd.iotbay.repository.Repository;

import java.util.Optional;

public interface UserRepository extends Repository<User> {

    Optional<User> findByUsername(String username);
    
    static UserRepository create(MongoDatabaseProvider datasource) {
        if (datasource == null) {
            return InMemoryUserRepository.concurrent();
        }
        return new MongoUserRepository(datasource);
    }
}
