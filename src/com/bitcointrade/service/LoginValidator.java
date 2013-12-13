package com.bitcointrade.service;

import com.bitcointrade.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: Augie
 * Date: 11/21/13
 * Time: 2:27 PM
 * <p/>
 * Modification:
 * ----------------------------
 */


public class LoginValidator {
    public boolean validate(User user) {
        return (user.getUserId().equals("augie") && user.getPassword().equals("password"));
    }
}
