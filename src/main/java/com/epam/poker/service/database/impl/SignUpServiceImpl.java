package com.epam.poker.service.database.impl;

import com.epam.poker.dao.ProfilePlayerDao;
import com.epam.poker.dao.UserDao;
import com.epam.poker.dao.helper.DaoTransactionProvider;
import com.epam.poker.dao.impl.user.ProfilePlayerDaoImpl;
import com.epam.poker.dao.impl.user.UserDaoImpl;
import com.epam.poker.exception.DaoException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.database.ProfilePlayer;
import com.epam.poker.model.database.User;
import com.epam.poker.service.database.SignUpService;
import com.epam.poker.service.validator.Validator;
import com.epam.poker.service.validator.impl.ProfilePlayerValidator;
import com.epam.poker.service.validator.impl.UserValidator;

import java.util.Optional;

public class SignUpServiceImpl implements SignUpService {
    private static final Validator<User> userValidator = UserValidator.getInstance();
    private static final Validator<ProfilePlayer> profilePlayerValidator = ProfilePlayerValidator.getInstance();
    private static SignUpService instance;

    private SignUpServiceImpl() {
    }

    public static SignUpService getInstance() {
        if (instance == null) {
            instance = new SignUpServiceImpl();
        }
        return instance;
    }

    @Override
    public long signUp(User user, ProfilePlayer profilePlayer) throws ServiceException {
        if (!userValidator.isValid(user) || !profilePlayerValidator.isValid(profilePlayer)) {
            return -1;
        }
        long userId = -1;
        UserDao userDao = new UserDaoImpl();
        ProfilePlayerDao profilePlayerDao = new ProfilePlayerDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(userDao, profilePlayerDao);
            userId = userDao.add(user);
            profilePlayer.setUserId(userId);
            profilePlayerDao.add(profilePlayer);
            transaction.commit();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return userId;
    }

    @Override
    public boolean isUserLoginExist(String login) throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(userDao);
            Optional<User> user = userDao.findUserByLogin(login);
            return user.isPresent();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isUserEmailExist(String email) throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(userDao);
            Optional<User> user = userDao.findUserByEmail(email);
            return user.isPresent();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
