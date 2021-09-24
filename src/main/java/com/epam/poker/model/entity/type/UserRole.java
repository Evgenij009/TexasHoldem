package com.epam.poker.model.entity.type;

import java.util.HashSet;
import java.util.Set;

import static com.epam.poker.controller.command.constant.CommandName.*;

public enum UserRole {
    ADMIN(HOME_PAGE, LOCALIZATION, BLOCK_USER, UNBLOCK_USER, USERS, PROFILE_PAGE, LOGOUT, UPLOAD_PHOTO, CHAT_PAGE),
    USER(HOME_PAGE, LOCALIZATION, PROFILE_PAGE, LOGOUT, UPLOAD_PHOTO, PLAY_PAGE, CHAT_PAGE),
    GUEST(HOME_PAGE, LOCALIZATION, SIGN_UP, SIGN_UP_PAGE, LOGIN, LOGIN_PAGE, CHECK_EXIST_LOGIN, CHECK_EXIST_EMAIL, PLAY_PAGE),
    MANAGER;
    private Set<String> commandsName = new HashSet<>();

    UserRole(String... commandsName) {
        this.commandsName.addAll(Set.of(commandsName));
    }

    public boolean isExistCommandName(String command) {
        return this.commandsName.contains(command);
    }
}