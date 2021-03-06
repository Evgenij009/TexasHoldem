package com.epam.poker.controller.command.impl.general;

import com.epam.poker.controller.command.Command;
import com.epam.poker.controller.command.CommandResult;
import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.exception.InvalidParametersException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.util.constant.Attribute;
import com.epam.poker.util.constant.PagePath;

public class GoToNotFoundPageCommand implements Command {
    public static final GoToNotFoundPageCommand INSTANCE = new GoToNotFoundPageCommand();
    private static final String MESSAGE_ERROR = "404 Page not found";

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
        requestContext.addAttribute(Attribute.ERROR_MESSAGE, MESSAGE_ERROR);
        return CommandResult.forward(PagePath.ERROR);
    }
}