package ru.the_boring_developers.common.repository.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.the_boring_developers.common.entity.user.User;
import ru.the_boring_developers.common.utils.DbUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@RequiredArgsConstructor
public class UserResultSetExtractorImpl implements UserResultSetExtractor {

    @Override
    public User extractValue(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setPassword(rs.getString("password"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setPatronymic(rs.getString("patronymic"));
        user.setPhoneNumber(rs.getString("phone_number"));
        user.setEmail(rs.getString("email"));
        user.setGender(rs.getBoolean("gender"));
        user.setDescription(rs.getString("description"));
        user.setAge(rs.getLong("age"));
        user.setLoginTime(DbUtils.getOffsetDateTime(rs, "login_time"));
        user.setPasswordChanged(DbUtils.getOffsetDateTime(rs, "password_changed"));
        return user;
    }
}
