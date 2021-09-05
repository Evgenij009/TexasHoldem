package com.epam.poker.dao.helper;

import com.epam.poker.connection.ConnectionPool;
import com.epam.poker.connection.ProxyConnection;
import com.epam.poker.dao.*;
import com.epam.poker.dao.impl.*;
import com.epam.poker.exception.DaoException;

import java.sql.SQLException;

public class DaoSaveTransaction implements AutoCloseable {
    private ProxyConnection connection;

    public DaoSaveTransaction(ConnectionPool pool) {
        this.connection = pool.getConnection();
    }

    public UserDao createUserDao() {
        return new UserDaoImpl(connection);
    }

    public ProfilePlayerDao createProfilePlayerDao() {
        return new ProfilePlayerDaoImpl(connection);
    }

    public GamePlayerDao createGamePlayerDao() {
        return new GamePlayerDaoImpl(connection);
    }

    public GameDao createGameDao() {
        return new GameDaoImpl(connection);
    }

    public GameWinnerDao createGameWinnerDao() {
        return new GameWinnerDaoImpl(connection);
    }

    public void startTransaction() throws DaoException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    private void rollback() throws DaoException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void commit() throws DaoException {
        try {
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                throw new DaoException(rollbackException);
            }
        }
    }

    @Override
    public void close() throws DaoException {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
    }
}
