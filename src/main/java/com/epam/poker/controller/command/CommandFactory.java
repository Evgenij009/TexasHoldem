package com.epam.poker.controller.command;

import com.epam.poker.controller.command.constant.CommandName;
import com.epam.poker.controller.command.impl.general.ForwardPageCommand;
import com.epam.poker.controller.command.impl.general.LocalizationCommand;
import com.epam.poker.controller.command.impl.user.*;
import com.epam.poker.model.dao.helper.DaoSaveTransactionFactory;
import com.epam.poker.model.logic.service.user.ProfilePlayerServiceImpl;
import com.epam.poker.model.logic.service.user.SignUpServiceImpl;
import com.epam.poker.model.logic.service.user.UserServiceImpl;
import com.epam.poker.model.logic.validator.impl.ProfilePlayerValidator;
import com.epam.poker.model.logic.validator.impl.UserValidator;

public class CommandFactory {

    public static Command createCommand(String commandParam) {
        if (commandParam == null) {
            throw new IllegalArgumentException("There is no command to do.");
        }
        return switch (commandParam) {
            case CommandName.SIGN_UP -> new SignUpCommand(new SignUpServiceImpl(new DaoSaveTransactionFactory(),
                    new UserValidator(), new ProfilePlayerValidator()));
            case CommandName.LOGIN ->  new LoginCommand(new UserServiceImpl(new DaoSaveTransactionFactory()),
                    new ProfilePlayerServiceImpl(new DaoSaveTransactionFactory(), new ProfilePlayerValidator()));
            case CommandName.LOGOUT ->  new LogoutCommand();
            case CommandName.SIGN_UP_PAGE, CommandName.HOME_PAGE, CommandName.LOGIN_PAGE -> new ForwardPageCommand(commandParam);
            case CommandName.PROFILE_PAGE -> new ProfilePageCommand(new ProfilePlayerServiceImpl(
                    new DaoSaveTransactionFactory(), new ProfilePlayerValidator()),
                    new UserServiceImpl(new DaoSaveTransactionFactory()));
            case CommandName.LOCALIZATION -> new LocalizationCommand();
            case CommandName.UPLOAD_PHOTO -> new UploadPhotoCommand(new ProfilePlayerServiceImpl(new DaoSaveTransactionFactory(), new ProfilePlayerValidator()));



            //AJAX
            case CommandName.CHECK_EXIST_LOGIN,
                    CommandName.CHECK_EXIST_EMAIL -> new CheckExistUsername(new SignUpServiceImpl(new DaoSaveTransactionFactory(), new UserValidator(), new ProfilePlayerValidator()));

            default -> throw new IllegalArgumentException("Unknown command: " + commandParam);
        };
    }
}