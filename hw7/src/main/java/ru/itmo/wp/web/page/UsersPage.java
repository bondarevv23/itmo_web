package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/** @noinspection unused*/
public class UsersPage extends AbstractPage {
    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", userService.findAll());
    }

    private void findUser(HttpServletRequest request, Map<String, Object> view) {
        view.put("user", userService.find(Long.parseLong(request.getParameter("userId"))));
    }

    private void changeRights (HttpServletRequest request, Map<String, Object> view) {
        Long id = tryParseLong(request.getParameter("userId"));
        if (Objects.isNull(getUser()) || !getUser().isAdmin() || Objects.isNull(id)) {
            throw new RedirectException("/index");
        }
        User user = userService.find(id);
        userService.changeRights(user);
        if (user.getId() == getUser().getId()) {
            getUser().setAdmin(false);
            throw new RedirectException("/users");
        }
        view.put("userRights", user.isAdmin());
    }
}
