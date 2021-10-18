package com.epam.poker.dao;

import com.epam.poker.exception.DaoException;
import com.epam.poker.model.database.ProfilePlayer;

import java.math.BigDecimal;
import java.util.List;

/*
 *   Extending DAO interface for processing ProfilePlayer
 */
public interface ProfilePlayerDao extends Dao<ProfilePlayer> {
        /**
         * Gets profile players amount in database.
         *
         * @return  an amount of all profile players.
         *
         * @throws  DaoException  if database errors occurs.
         */
        int findProfilePlayerAmount() throws DaoException;
        boolean updateProfilePlayerByUserId(long userId, ProfilePlayer profilePlayer) throws DaoException;
        boolean updatePhotoByUserId(long userId, String photo) throws DaoException;
        boolean updateBestPrizeByUserId(long userId, BigDecimal bestPrize) throws DaoException;
        boolean updateAwardByUserId(long userId, String award) throws DaoException;
        boolean updateAboutYourselfByUserId(long userId, String aboutYourself) throws DaoException;
        boolean updateLostMoneyByUserId(long userId, BigDecimal money) throws DaoException;
        boolean updateWinMoneyByUserId(long userId, BigDecimal money) throws DaoException;
        List<ProfilePlayer> findProfilePlayerOfRange(int offset, int amount) throws DaoException;
        /**
         *
         * @param profilePlayer field userId should be
         * @return
         * @throws DaoException
         */
        boolean add(ProfilePlayer profilePlayer) throws DaoException;
}
