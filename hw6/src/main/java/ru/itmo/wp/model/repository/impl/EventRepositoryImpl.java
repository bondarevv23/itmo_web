package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.repository.EventRepository;

import javax.sql.DataSource;
import java.sql.*;

public class EventRepositoryImpl extends AbstractRepository<Event> implements EventRepository {
    public EventRepositoryImpl() {
        super(Event::new);
    }

    @Override
    public void save(long userId, Event.Type type) {
        super.save("INSERT INTO `Event` (`userId`, `type`, `creationTime`) VALUES (?, ?, NOW())", userId, type);
    }

    @Override
    protected void beforeSave(PreparedStatement statement, Object[] args) throws SQLException {
        statement.setLong(1, (long) args[0]);
        statement.setString(2, ((Event.Type)args[1]).name());
    }

    @Override
    protected void afterSave(ResultSet generatedKeys, Object[] args) throws SQLException {
        // No operations.
    }
}
