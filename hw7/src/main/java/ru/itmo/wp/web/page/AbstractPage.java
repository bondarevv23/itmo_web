package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractPage {
    protected final UserService userService = new UserService();
    protected final ArticleService articleService = new ArticleService();
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

    protected Long tryParseLong (String str) {
        if (str == null) {
            return null;
        }
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException exception) {
            return null;
        }
    }
}
