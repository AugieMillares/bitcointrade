package com.bitcointrade.model;

/**
 * Created with IntelliJ IDEA.
 * User: Augie
 * Date: 11/21/13
 * Time: 2:24 PM
 * <p/>
 * Modification:
 * ----------------------------
 */


public class User {
    String userId;
    String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
