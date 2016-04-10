package com.alevsk.bot.facebook.util;

/**
 * Created by lehuerta on 07/04/2016.
 */
public class FacebookMessage {
    private String hash;
    private String time;
    private String name;
    private String message;

    public FacebookMessage(String hash, String time, String name, String message) {
        this.hash = hash;
        this.time = time;
        this.name = name;
        this.message = message;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
