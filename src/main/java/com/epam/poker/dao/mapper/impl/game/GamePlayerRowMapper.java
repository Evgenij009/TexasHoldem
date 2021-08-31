package com.epam.poker.dao.mapper.impl.game;

import com.epam.poker.dao.mapper.RowMapper;
import com.epam.poker.model.entity.game.GamePlayer;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.poker.dao.ColumnName.*;

public class GamePlayerRowMapper implements RowMapper<GamePlayer> {
    @Override
    public GamePlayer map(ResultSet resultSet) throws SQLException {
        GamePlayer gamePlayer = GamePlayer.builder()
                .setGamePlayerId(resultSet.getLong(GAME_PLAYER_ID))
                .setLastAction(resultSet.getString(GAME_PLAYER_LAST_ACTION))
                .setTwoCards(resultSet.getString(GAME_PLAYER_TWO_CARDS))
                .setCombinationsCards(resultSet.getString(GAME_PLAYER_COMBINATIONS_CARDS))
                .setUserId(resultSet.getLong(GAME_PLAYER_USER_ID))
                .setGameId(resultSet.getLong(GAME_PLAYER_GAME_ID))
                .createGamePlayer();
        return gamePlayer;
    }
}
