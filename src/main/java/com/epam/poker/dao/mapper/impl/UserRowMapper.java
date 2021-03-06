package com.epam.poker.dao.mapper.impl;

import com.epam.poker.dao.mapper.RowMapper;
import com.epam.poker.model.database.User;
import com.epam.poker.model.database.type.UserRole;
import com.epam.poker.model.database.type.UserStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.poker.dao.ColumnName.*;

public class UserRowMapper implements RowMapper<User> {
    private static UserRowMapper instance;

    public static UserRowMapper getInstance() {
        if (instance == null) {
            instance = new UserRowMapper();
        }
        return instance;
    }

    @Override
    public User map(ResultSet resultSet) throws SQLException {
        return User.builder()
                .setUserId(resultSet.getLong(USER_ID))
                .setLogin(resultSet.getString(USER_LOGIN))
                .setPassword(resultSet.getString(USER_PASSWORD))
                .setFirstName(resultSet.getString(USER_FIRST_NAME))
                .setLastName(resultSet.getString(USER_LAST_NAME))
                .setEmail(resultSet.getString(USER_EMAIL))
                .setBalance(resultSet.getBigDecimal(USER_BALANCE))
                .setUserRole(UserRole.valueOf(resultSet.getString(USER_ROLE)))
                .setUserStatus(UserStatus.valueOf(resultSet.getString(USER_STATUS)))
                .setPhoneNumber(Long.parseLong(resultSet.getString(USER_PHONE_NUMBER)))
                .setCreateTime(resultSet.getTimestamp(USER_CREATE_TIME))
                .createUser();
    }
}
