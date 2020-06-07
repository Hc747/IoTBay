package au.edu.uts.isd.iotbay.repository.log;

import au.edu.uts.isd.iotbay.model.log.UserLog;
import au.edu.uts.isd.iotbay.model.user.User;
import au.edu.uts.isd.iotbay.repository.InMemoryRepository;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public class InMemoryUserLogRepository extends InMemoryRepository<UserLog> implements UserLogRepository {

    @Override
    public Collection<UserLog> findByUser(User user) {
        return findAll(log -> log.getUser().equals(user));
    }

    @Override
    public Collection<UserLog> findByUserBeforeDate(User user, Date date) {
        final ChronoLocalDate timestamp = LocalDate.from(date.toInstant());
        return findByUser(user).stream().filter(log -> log.getDate().isBefore(timestamp)).collect(Collectors.toList());
    }
}

