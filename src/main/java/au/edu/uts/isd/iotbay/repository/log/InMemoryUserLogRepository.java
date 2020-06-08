package au.edu.uts.isd.iotbay.repository.log;

import au.edu.uts.isd.iotbay.model.log.UserLog;
import au.edu.uts.isd.iotbay.model.user.User;
import au.edu.uts.isd.iotbay.repository.InMemoryRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

public class InMemoryUserLogRepository extends InMemoryRepository<UserLog> implements UserLogRepository {

    @Override
    public Collection<UserLog> findByUser(User user) {
        return findAll(log -> log.getUser().equals(user));
    }

    //retrieves the user logs before the selected date
    @Override
    public Collection<UserLog> findByUserBeforeDate(User user, LocalDate date) {
        return findByUser(user).stream().filter(log -> log.getDate().isBefore(date)).collect(Collectors.toList());
    }
}

