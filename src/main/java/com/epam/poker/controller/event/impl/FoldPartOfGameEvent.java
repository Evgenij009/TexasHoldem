package com.epam.poker.controller.event.impl;

import com.epam.poker.controller.event.EventSocket;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.game.Gambler;
import com.epam.poker.model.game.Lobby;
import com.epam.poker.model.game.Table;
import com.epam.poker.service.game.EventHandlerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

import static com.epam.poker.util.constant.Attribute.*;

public class FoldPartOfGameEvent implements EventSocket {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Lobby lobby = Lobby.getInstance();
    private static final FoldPartOfGameEvent instance = new FoldPartOfGameEvent();
    private static final EventHandlerService eventHandlerService = EventHandlerService.getInstance();
    private static final List<String> PARTS_OF_GAMES = List.of(PRE_FLOP_PART_OF_GAME,
            FLOP_PART_OF_GAME, TURN_PART_OF_GAME, RIVER_PART_OF_GAME);

    private FoldPartOfGameEvent() {
    }

    @Override
    public void execute(String jsonLine, Gambler gambler) {
        JsonNode json = null;
        try {
            json = mapper.readTree(jsonLine);
        } catch (JsonProcessingException e) {
            LOGGER.error("Parse json: " + e);
        }
        ObjectNode response = mapper.createObjectNode();
        response.putPOJO(EVENT, json.get(EVENT));
        ObjectNode objectNode = mapper.createObjectNode();
        if (gambler.getSittingOnTable() > -1
                && lobby.findTableByNameRoom(gambler.getTitleRoom()) != null) {
            Table table = lobby.findTableByNameRoom(gambler.getTitleRoom());
            int activeSeat = table.getActiveSeat();
            if (table.getSeats()[activeSeat] == gambler
                    && PARTS_OF_GAMES.contains(table.getPhaseGame())) {
                objectNode.put(SUCCESS, true);
                sendEvent(gambler, response, objectNode);
                try {
                    eventHandlerService.gamblerFolded(table, gambler);
                } catch (ServiceException e) {
                    LOGGER.error("Service game error: " + e);
                }
            }
        }
    }

    private void sendEvent(Gambler gambler, ObjectNode response, ObjectNode objectNode) {
        response.putPOJO(DATA, objectNode);
        try {
            gambler.getSession().getBasicRemote().sendText(String.valueOf(response));
        } catch (IOException e) {
            LOGGER.error("Send JSON: " + e);
        }
    }

    public static FoldPartOfGameEvent getInstance() {
        return instance;
    }
}
