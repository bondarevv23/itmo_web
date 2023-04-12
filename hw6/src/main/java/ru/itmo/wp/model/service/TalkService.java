package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.TalkRepository;
import ru.itmo.wp.model.repository.impl.TalkRepositoryImpl;
import ru.itmo.wp.web.exception.RedirectException;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class TalkService {
    private final TalkRepository talkRepository = new TalkRepositoryImpl();

    public void send(Talk talk) {
        talkRepository.save(talk);
    }

    public void validate(Talk talk, User sourceUser, User targetUser)  throws ValidationException, RedirectException {
        if (Objects.isNull(sourceUser)) {
//            throw new ValidationException("You need to register to send a message");
            throw new RedirectException("/index");
        }
        talk.setSourceUserId(sourceUser.getId());

        if (Objects.isNull(targetUser)) {
            throw new ValidationException("User with such login doesn't exist");
        }
        talk.setTargetUserId(targetUser.getId());

        if (Strings.isNullOrEmpty(talk.getText())) {
            throw new ValidationException("Message should have a text");
        }
        if (talk.getText().length() >= 500) {
            throw new ValidationException("Long Message");
        }
        if (talk.getText().trim().isEmpty()) {
            throw new ValidationException("Empty message");
        }

        if (talk.getSourceUserId() == talk.getTargetUserId()) {
            throw new ValidationException("You can't send a message to yourself");
        }
    }

    public List<Talk> getTalks(long userId) {
        return talkRepository.getTalks(userId);
    }
}
