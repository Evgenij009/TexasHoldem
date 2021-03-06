package com.epam.poker.controller.event.impl;

import com.epam.poker.controller.event.EventSocket;
import com.epam.poker.controller.event.util.ValidationSitOnTableEventHelper;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.database.User;
import com.epam.poker.model.game.Gambler;
import com.epam.poker.model.game.Lobby;
import com.epam.poker.model.game.Table;
import com.epam.poker.service.database.UserService;
import com.epam.poker.service.database.impl.UserServiceImpl;
import com.epam.poker.service.game.EventHandlerService;
import com.epam.poker.util.constant.Attribute;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;

import static com.epam.poker.util.constant.Attribute.DATA;

public class SitOnTheTableEvent implements EventSocket {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final SitOnTheTableEvent instance = new SitOnTheTableEvent();
    private static final Lobby lobby = Lobby.getInstance();
    private static final ValidationSitOnTableEventHelper validationJsonData = ValidationSitOnTableEventHelper.getInstance();
    private static final UserService userService = UserServiceImpl.getInstance();
    private static final EventHandlerService eventHandlerService = EventHandlerService.getInstance();
    private static final String MESSAGE_ERROR_CHIPS = "Your chips is invalid!";
    private static final String MESSAGE_ERROR_COMPARE_CHIPS_MAX_MIN_TABLE =
            "The amount of chips should be between the maximum and the minimum amount of allowed buy in";

    private SitOnTheTableEvent() {
    }

    public static SitOnTheTableEvent getInstance() {
        return instance;
    }

    @Override
    public void execute(String jsonRequest, Gambler gambler) {
        JsonNode json = null;
        try {
            json = mapper.readTree(jsonRequest);
        } catch (JsonProcessingException e) {
            LOGGER.error("Parse json: " + e);
        }
        User user = null;
        try {
            user = userService.findUserByLogin(gambler.getName());
        } catch (ServiceException e) {
            LOGGER.error("Select user from database: " + e);
        }
        JsonNode data;
        ObjectNode objectNode = mapper.createObjectNode();
        try {
            ObjectNode response = mapper.createObjectNode();
            response.putPOJO(Attribute.EVENT, json.get(Attribute.EVENT));
            BigDecimal balanceGambler = user.getBalance();
            gambler.setBalance(balanceGambler);
            data = json.get(Attribute.DATA);
            int numberSeat = data.get(Attribute.SEAT).asInt();
            long tableId = data.get(Attribute.TABLE_ID).asLong();
            BigDecimal bet = new BigDecimal(String.valueOf(data.get(Attribute.CHIPS)));
            if (validationJsonData.isValidSitOnTheTableEvent(gambler, numberSeat, tableId)) {
                if (bet.compareTo(balanceGambler) > 0) {
                    objectNode.put(Attribute.SUCCESS, false);
                    objectNode.put(Attribute.ERROR, MESSAGE_ERROR_CHIPS);
                    sendEvent(gambler, response, objectNode);
                } else if (!isValidMinMaxBetOnTable(tableId, bet)) {
                    objectNode.put(Attribute.SUCCESS, false);
                    objectNode.put(Attribute.ERROR, MESSAGE_ERROR_COMPARE_CHIPS_MAX_MIN_TABLE);
                    sendEvent(gambler, response, objectNode);
                } else {
                    objectNode.put(Attribute.SUCCESS, true);
                    sendEvent(gambler, response, objectNode);
                    Table table = lobby.findTableByNameRoom(String.format(Attribute.TABLE_WITH_HYPHEN, tableId));
                    eventHandlerService.gamblerSitOnTheTable(table, gambler, numberSeat, bet);
                }
            } else {
                objectNode.put(Attribute.SUCCESS, false);
                sendEvent(gambler, response, objectNode);
            }
        } catch (NumberFormatException e) {
            LOGGER.error("JSON line don't exist data. " + e);
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

    private boolean isValidMinMaxBetOnTable(long tableId, BigDecimal chips) {
        Table table = lobby.findTableByNameRoom(String.format(Attribute.TABLE_WITH_HYPHEN, tableId));
        return  table != null && chips.compareTo(table.getMaxBuyIn()) <= 0
                && chips.compareTo(table.getMinBuyIn()) >= 0;
    }
}