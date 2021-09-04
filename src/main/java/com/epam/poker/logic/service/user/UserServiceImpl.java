package com.epam.poker.logic.service.user;

import com.epam.poker.dao.UserDao;
import com.epam.poker.dao.helper.DaoSaveTransaction;
import com.epam.poker.dao.helper.DaoSaveTransactionFactory;
import com.epam.poker.exception.DaoException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.entity.User;
import com.epam.poker.model.enumeration.UserStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService{
    private final DaoSaveTransactionFactory daoSaveTransactionFactory;

    public UserServiceImpl(DaoSaveTransactionFactory daoSaveTransactionFactory) {
        this.daoSaveTransactionFactory = daoSaveTransactionFactory;
    }

    @Override
    public List<User> findUsersRange(int offset, int amount) throws ServiceException {
        return null;
    }

    @Override
    public int findUsersAmount() throws ServiceException {
        return 0;
    }

    @Override
    public Optional<User> findUserByLoginPassword(String login, String password) throws ServiceException {
        return Optional.empty();
    }

    @Override
    public User findUserByLogin(String login) throws ServiceException {
        try (DaoSaveTransaction daoSaveTransaction = daoSaveTransactionFactory.create()) {
            UserDao userDao = daoSaveTransaction.createUserDao();
            Optional<User> user = userDao.findUserByLogin(login);
            if (!user.isPresent()) {
                throw new ServiceException("User with login=" + login + " is not found.");
            }
            return user.get();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User findUserByEmail(String email) throws ServiceException {
        try (DaoSaveTransaction daoSaveTransaction = daoSaveTransactionFactory.create()) {
            UserDao userDao = daoSaveTransaction.createUserDao();
            Optional<User> user = userDao.findUserByEmail(email);
            if (!user.isPresent()) {
                throw new ServiceException("User with email=" + email + " is not found.");
            }
            return user.get();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean blockById(long id) throws ServiceException {
        return false;
    }

    @Override
    public boolean unblockById(long id) throws ServiceException {
        return false;
    }

    @Override
    public boolean addMoneyById(BigDecimal money, long id) throws ServiceException {
        return false;
    }

    @Override
    public boolean update(User item) throws ServiceException {
        return false;
    }

    @Override
    public long add(User t) throws ServiceException {
        return 0;
    }

    @Override
    public boolean updatePassword(long userId, String password) throws ServiceException {
        return false;
    }

    @Override
    public boolean isBlockedById(long id) throws ServiceException {
        try (DaoSaveTransaction daoSaveTransaction = daoSaveTransactionFactory.create()) {
            UserDao userDao = daoSaveTransaction.createUserDao();
            Optional<User> user = userDao.findById(id);
            if (!user.isPresent()) {
                throw new ServiceException("User with id=" + id + " is not found.");
            }
            return user.get().getUserStatus() == UserStatus.BANNED;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isUserExistByLoginPassword(String login, String password) throws ServiceException {
        try (DaoSaveTransaction daoSaveTransaction = daoSaveTransactionFactory.create()) {
            UserDao userDao = daoSaveTransaction.createUserDao();
            Optional<User> user = userDao.findUserByLoginPassword(login, password);
            return user.isPresent();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}