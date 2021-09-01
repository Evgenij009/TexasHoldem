package com.epam.poker.mapper.impl;

import com.epam.poker.mapper.RowMapper;
import com.epam.poker.model.entity.User;
import com.epam.poker.model.enumeration.UserRole;
import com.epam.poker.model.enumeration.UserStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.poker.dao.ColumnName.*;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User map(ResultSet resultSet) throws SQLException {
        return User.builder()
                .setUserId(resultSet.getLong(USER_ID))
                .setLogin(resultSet.getString(USER_LOGIN))
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