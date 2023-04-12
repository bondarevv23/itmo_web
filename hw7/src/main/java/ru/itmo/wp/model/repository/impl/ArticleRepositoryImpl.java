package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.repository.ArticleRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ArticleRepositoryImpl extends AbstractRepository<Article> implements ArticleRepository {
    public ArticleRepositoryImpl() {
        super(Article::new);
    }

    @Override
    public void save(Article article) {
        super.save("INSERT INTO `Article` (`userId`, `title`, `text`, `creationTime`, `hidden`) VALUES (?, ?, ?, NOW(), ?)", article);
    }

    @Override
    public List<Article> getAllReverseOrder() {
        return doSQLRequest("SELECT * FROM Article WHERE NOT hidden ORDER BY creationTime DESC");
    }

    @Override
    public List<Article> getArticlesByUserId(long id) {
        return doSQLRequest("SELECT * FROM Article WHERE userId = ?  ORDER BY creationTime DESC", id);
    }

    @Override
    protected void beforeSave(PreparedStatement statement, Object[] args) throws SQLException {
        final Article article = (Article) args[0];
        statement.setLong(1, article.getUserId());
        statement.setString(2, article.getTitle());
        statement.setString(3, article.getText());
        statement.setBoolean(4, Boolean.FALSE);
    }

    @Override
    protected void afterSave(ResultSet generatedKeys, Object[] args) throws SQLException {
        final Article article = (Article) args[0];
        article.setId(generatedKeys.getLong(1));
        article.setCreationTime(find(article.getId()).getCreationTime());
    }

    @Override
    public Article find(long id) {
        return super.find(id);
    }

    @Override
    public void changeHidden(Article article) {
        doSQLRequest("UPDATE `Article` SET hidden = ? WHERE id = ?", !article.isHidden(), article.getId());
    }
}
