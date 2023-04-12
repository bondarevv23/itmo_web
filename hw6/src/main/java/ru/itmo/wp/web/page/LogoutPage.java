package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings({"unused"})
public class LogoutPage extends AbstractPage  {
    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        if (Objects.isNull(getUser())) {
            setMessage("You have already logged out");
            throw new RedirectException("/index");
        }
        eventService.note(getUser().getId(), Event.Type.LOGOUT);
        request.getSession().removeAttribute("user");

        request.getSession().setAttribute("message", "Good bye. Hope to see you soon!");
        throw new RedirectException("/index");
    }
}
