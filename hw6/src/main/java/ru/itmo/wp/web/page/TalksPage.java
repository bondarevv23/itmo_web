package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.DTO.TalkDTO;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TalksPage extends AbstractPage {
    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        if (Objects.isNull(getUser())) {
            throw new RedirectException("/index");
        }
        putTalks(view);
    }

    @Override
    protected void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
        view.put("users", userService.findAll());
        putTalks(view);
    }

    private void sendMessage(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        Talk talk = new Talk();
        talk.setText(request.getParameter("text"));
        talkService.validate(talk, getUser(), userService.findByLogin(request.getParameter("targetUser")));
        talkService.send(talk);
        throw new RedirectException("/talks");
    }

    private void putTalks(Map<String, Object> view) {
        if (Objects.isNull(getUser())) {
            return;
        }
        List<TalkDTO> talks = talkService.getTalks(getUser().getId()).stream().map(
                t -> new TalkDTO(
                        t.getId(),
                        userService.findById(t.getSourceUserId()).getLogin(),
                        userService.findById(t.getTargetUserId()).getLogin(),
                        t.getText(),
                        t.getCreationTime()
                )).toList();
        view.put("talks", talks);
    }
}
