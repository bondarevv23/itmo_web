package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ArticleService {
    private final ArticleRepository articleRepository = new ArticleRepositoryImpl();

    public Article tryCreate(long userId, String title, String text) throws ValidationException {
        if (Objects.isNull(title)) {
            throw new ValidationException("Article should have a title");
        }
        title = title.trim();
        if (title.isEmpty()) {
            throw new ValidationException("Empty title");
        }
        if (title.length() < 6) {
            throw new ValidationException("Title must contain at least 6 characters");
        }
        if (title.length() > 255) {
            throw new ValidationException("Title must contain no more than 255 characters");
        }

        if (Strings.isNullOrEmpty(text)) {
            throw new ValidationException("Article should have a text");
        }
        if (text.length() > 1000) {
            throw new ValidationException("Title must contain no more than 1000 characters");
        }

        Article article = new Article();
        article.setUserId(userId);
        article.setTitle(title);
        article.setText(text);
        return article;
    }

    public void save(Article article) {
        articleRepository.save(article);
    }

    public List<Article> getAllReverseOrder() {
        return articleRepository.getAllReverseOrder();
    }

    public List<Article> getArticlesByUser(User user) {
        return articleRepository.getArticlesByUserId(user.getId());
    }

    public Article find(long id) {
        return articleRepository.find(id);
    }

    public void changeHidden (Article article) {
        articleRepository.changeHidden(article);
    }
}
