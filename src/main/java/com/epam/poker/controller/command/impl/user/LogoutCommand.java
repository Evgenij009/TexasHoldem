package com.epam.poker.controller.command.impl.user;

import com.epam.poker.controller.command.Command;
import com.epam.poker.controller.command.CommandResult;
import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.exception.InvalidParametersException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.util.constant.Attribute;
import com.epam.poker.util.constant.CommandName;
import jakarta.servlet.http.Cookie;

public class LogoutCommand implements Command {
    private static final String HOME_PAGE_COMMAND = "poker?command=" + CommandName.HOME_PAGE;

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
        Cookie cookie = new Cookie(Attribute.ACCESS_TOKEN, null);
        cookie.setMaxAge(0);
        requestContext.addCookie(cookie);
        requestContext.addSession(Attribute.INVALIDATE_ATTRIBUTE, true);
        return CommandResult.redirect(HOME_PAGE_COMMAND);
    }
}
