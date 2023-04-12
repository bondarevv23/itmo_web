package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.repository.TalkRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class TalkRepositoryImpl extends AbstractRepository<Talk> implements TalkRepository {
    public TalkRepositoryImpl() {
        super(Talk::new);
    }

    @Override
    public List<Talk> getTalks(long userId) {
        return doSQLRequest("SELECT * FROM Talk WHERE sourceUserId LIKE ? OR targetUserId LIKE ? ORDER BY creationTime", userId, userId);
    }

    @Override
    public void save(Talk talk) {
        super.save("INSERT INTO `Talk` (`sourceUserId`, `targetUserId`, `text`, `creationTime`) VALUES (?, ?, ?, NOW())");
    }

    @Override
    protected void beforeSave(PreparedStatement statement, Object[] args) throws SQLException {
        final Talk talk = (Talk) args[0];
        statement.setLong(1, talk.getSourceUserId());
        statement.setLong(2, talk.getTargetUserId());
        statement.setString(3, talk.getText());
    }

    @Override
    protected void afterSave(ResultSet generatedKeys, Object[] args) throws SQLException {
        final Talk talk = (Talk) args[0];
        talk.setId(generatedKeys.getLong(1));
        talk.setCreationTime(find(talk.getId()).getCreationTime());
    }

    @Override
    public Talk find(long id) {
        return super.find(id);
    }

    private Talk toTalk(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        return super.toModel(metaData, resultSet);
    }
}
