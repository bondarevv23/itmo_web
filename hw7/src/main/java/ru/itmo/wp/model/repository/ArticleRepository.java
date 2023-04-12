package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Article;

import java.util.List;

public interface ArticleRepository {
    void save(Article article);

    List<Article> getAllReverseOrder();

    List<Article> getArticlesByUserId(long id);

    Article find(long id);

    void changeHidden (Article article);
}
