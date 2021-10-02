package com.epam.poker.game.entity;

import java.util.StringJoiner;

public class Log {
    private String message;
    private String seat;
    private String action;
    private String notification;

    public Log(String message, String seat, String action, String notification) {
        this.message = message;
        this.seat = seat;
        this.action = action;
        this.notification = notification;
    }

    public Log() {
        this.message = new String();
        this.action = new String();
        this.seat = new String();
        this.notification = new String();
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getMessage() {
        return message;
    }

    public Log setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getSeat() {
        return seat;
    }

    public Log setSeat(String seat) {
        this.seat = seat;
        return this;
    }

    public String getAction() {
        return action;
    }

    public Log setAction(String action) {
        this.action = action;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Log log = (Log) o;

        if (message != null ? !message.equals(log.message) : log.message != null) return false;
        if (seat != null ? !seat.equals(log.seat) : log.seat != null) return false;
        return action != null ? action.equals(log.action) : log.action == null;
    }

    @Override
    public int hashCode() {
        int result = message != null ? message.hashCode() : 0;
        result = 31 * result + (seat != null ? seat.hashCode() : 0);
        result = 31 * result + (action != null ? action.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Log.class.getSimpleName() + "[", "]")
                .add("message='" + message + "'")
                .add("seat='" + seat + "'")
                .add("action='" + action + "'")
                .toString();
    }
}