package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@SuppressWarnings("SqlNoDataSourceInspection")
public class UserRepositoryImpl extends AbstractRepository<User> implements UserRepository {
    public UserRepositoryImpl() {
        super(User::new);
    }

    @Override
    public User findByLogin(String login) {
        List<User> list = doSQLRequest("SELECT * FROM User WHERE login=?", login);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public User findByLoginAndPasswordSha(String login, String passwordSha) {
        return firstOrNull(doSQLRequest("SELECT * FROM User WHERE login=? AND passwordSha=?", login, passwordSha));
    }

    @Override
    public User findByEmailAndPasswordSha(String email, String passwordSha) {
        return firstOrNull(doSQLRequest("SELECT * FROM User WHERE email=? AND passwordSha=?", email, passwordSha));
    }

    @Override
    public List<User> findAll() {
        return doSQLRequest("SELECT * FROM User ORDER BY id DESC");
    }

    @Override
    public long findCount() {
        return count("SELECT COUNT(*) row_count FROM `User`");
    }

    @Override
    public void save(User user, String passwordSha) {
        super.save("INSERT INTO `User` (`login`, `email`, `passwordSha`, `creationTime`) VALUES (?, ?, ?, NOW())", passwordSha);
    }

    @Override
    protected void beforeSave(PreparedStatement statement, Object[] args) throws SQLException {
        final User user = (User) args[0];
        statement.setString(1, user.getLogin());
        statement.setString(2, user.getEmail());
        statement.setString(3, (String) args[1]);
    }

    @Override
    protected void afterSave(ResultSet generatedKeys, Object[] args) throws SQLException {
        final User user = (User) args[0];
        user.setId(generatedKeys.getLong(1));
        user.setCreationTime(find(user.getId()).getCreationTime());
    }

    @Override
    public User find(long id) {
        return super.find(id);
    }

    private User toUser(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        return toModel(metaData, resultSet);
    }
}
