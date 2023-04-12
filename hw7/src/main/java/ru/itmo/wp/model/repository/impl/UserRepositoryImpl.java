package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl extends AbstractRepository<User> implements UserRepository {
    public UserRepositoryImpl() {
        super(User::new);
    }

    @Override
    public User find(long id) {
        return super.find(id);
    }

    @Override
    protected void beforeSave(PreparedStatement statement, Object[] args) throws SQLException {
        final User user = (User) args[0];
        statement.setObject(1, user.getLogin());
        statement.setString(2, user.getEmail());
        statement.setString(3, (String) args[1]);
        statement.setBoolean(4, user.isAdmin());
    }

    @Override
    protected void afterSave(ResultSet generatedKeys, Object[] args) throws SQLException {
        final User user = (User) args[0];
        user.setId(generatedKeys.getLong(1));
        user.setCreationTime(find(user.getId()).getCreationTime());
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
    public List<User> findAll() {
        return doSQLRequest("SELECT * FROM User ORDER BY id DESC");
    }

    @Override
    public void save(User user, String passwordSha) {
        super.save("INSERT INTO `User` (`login`, `email`, `passwordSha`, `creationTime`, `admin`) VALUES (?, ?, ?, NOW(), ?)", user, passwordSha);
    }

    @Override
    public void changeRights(User user) {
        doSQLRequest("UPDATE `User` SET admin = ? WHERE id = ?", !user.isAdmin(), user.getId());
    }
}
