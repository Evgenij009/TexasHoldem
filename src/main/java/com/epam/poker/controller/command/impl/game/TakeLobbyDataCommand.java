package com.epam.poker.controller.command.impl.game;

import com.epam.poker.controller.command.Command;
import com.epam.poker.controller.command.CommandResult;
import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.exception.InvalidParametersException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.game.Lobby;
import com.epam.poker.model.game.Table;
import com.epam.poker.service.game.ParserDataToJsonService;

import java.util.ArrayList;
import java.util.List;

public class TakeLobbyDataCommand implements Command {
    private static final Lobby lobby = Lobby.getInstance();
    private static final ParserDataToJsonService parserDataToJson = ParserDataToJsonService.getInstance();

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
        List<String> lobbyTablesJson = new ArrayList<>();
        List<Table> tables = lobby.findAllTables();
        for (Table table : tables) {
            if (!table.isPrivateTable()) {
                lobbyTablesJson.add(parserDataToJson.parseDataTableForLobby(table));
            }
        }
        CommandResult commandResult = new CommandResult(true);
        commandResult.setJsonResponse(String.valueOf(lobbyTablesJson));
        return commandResult;
    }
}
