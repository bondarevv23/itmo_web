package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

public class ArticlePage extends AbstractPage {
    @Override
    protected void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
        if (Objects.isNull(getUser())) {
            throw new RedirectException("/index");
        }
    }

    private void create(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        Article article = articleService.tryCreate(
                getUser().getId(),
                request.getParameter("title"),
                request.getParameter("text")
        );
        articleService.save(article);
        view.put("message", "New article added successfully!");
    }
}
