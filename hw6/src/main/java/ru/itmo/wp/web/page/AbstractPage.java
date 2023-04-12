package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.EventService;
import ru.itmo.wp.model.service.TalkService;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractPage {
    protected final UserService userService = new UserService();
    protected final EventService eventService = new EventService();
    protected final TalkService talkService = new TalkService();
    private HttpSession session = null;

    protected void before(HttpServletRequest request, Map<String, Object> view) {
        session = request.getSession();

        if (Objects.nonNull(getUser())) {
            view.put("user", getUser());
        }

        String message = (String) request.getSession().getAttribute("message");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            request.getSession().removeAttribute("message");
        }

        view.put("userCount", userService.findCount());
    }

    protected void setMessage(String message) {
        session.setAttribute("message", message);
    }

    protected void setUser(User user) {
        session.setAttribute("user", user);
    }

    protected User getUser() {
        return (User) session.getAttribute("user");
    }

    protected void after(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }

    protected void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }
}
