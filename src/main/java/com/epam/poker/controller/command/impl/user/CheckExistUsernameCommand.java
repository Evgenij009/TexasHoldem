package com.epam.poker.controller.command.impl.user;

import com.epam.poker.controller.command.Command;
import com.epam.poker.controller.command.CommandResult;
import com.epam.poker.controller.command.util.ParameterTaker;
import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.service.database.SignUpService;
import com.epam.poker.service.database.impl.SignUpServiceImpl;
import com.epam.poker.util.constant.Attribute;
import com.epam.poker.util.constant.Parameter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class CheckExistUsernameCommand implements Command {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final SignUpService service = SignUpServiceImpl.getInstance();

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        boolean isExistUsername = true;
        String username = ParameterTaker.takeString(Parameter.LOGIN, requestContext);
        if (username == null) {
            username = ParameterTaker.takeString(Parameter.EMAIL, requestContext);
            if (username != null) {
                isExistUsername = service.isUserEmailExist(username);
            }
        } else {
            isExistUsername = service.isUserLoginExist(username);
        }
        ObjectNode response = mapper.createObjectNode();
        response.put(Attribute.CHECK_USERNAME_EXIST, String.valueOf(isExistUsername));
        CommandResult commandResult = new CommandResult(true);
            commandResult.setJsonResponse(response.toString());
        return commandResult;
    }
}
