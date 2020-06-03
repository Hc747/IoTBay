package au.edu.uts.isd.iotbay.repository.user;

import au.edu.uts.isd.iotbay.model.user.User;
import au.edu.uts.isd.iotbay.persistence.jdbc.ConnectionProvider;
import au.edu.uts.isd.iotbay.repository.Repository;

import java.util.Optional;

public interface UserRepository extends Repository<User> {

    Optional<User> findByUsername(String username);
    
    static UserRepository create(ConnectionProvider datasource) {
        if (datasource == null) {
            return InMemoryUserRepository.concurrent();
        }
        return new PersistentUserRepository(datasource);
    }
}
