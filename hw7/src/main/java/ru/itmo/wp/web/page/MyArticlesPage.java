package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

public class MyArticlesPage extends AbstractPage {
    @Override
    protected void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
        if (Objects.isNull(getUser())) {
            throw new RedirectException("/index");
        }
    }

    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        view.put("myArticles", articleService.getArticlesByUser(getUser()));
    }

    private void changeDisplay (HttpServletRequest request, Map<String, Object> view) {
        Long id = tryParseLong(request.getParameter("articleId"));
        if (Objects.isNull(id)) {
            throw new RedirectException("/index");
        }
        Article article = articleService.find(id);
        if (article.getUserId() == getUser().getId()) {
            articleService.changeHidden(article);
            view.put("displayType", !article.isHidden());
        }
    }
}
