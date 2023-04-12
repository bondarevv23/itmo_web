package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.repository.EventRepository;
import ru.itmo.wp.model.repository.impl.EventRepositoryImpl;


public class EventService {
    private final EventRepository eventRepository = new EventRepositoryImpl();

    public void note(long userId, Event.Type type) {
        eventRepository.save(userId, type);
    }
}
