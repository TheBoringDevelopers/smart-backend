package ru.the_boring_developers.common.repository.user;

import ru.the_boring_developers.common.entity.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface UserResultSetExtractor {

    User extractValue(ResultSet rs) throws SQLException;
}
