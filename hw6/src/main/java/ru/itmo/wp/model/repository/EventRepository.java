package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.User;

public interface EventRepository {
    void save(long userId, Event.Type type);
}
