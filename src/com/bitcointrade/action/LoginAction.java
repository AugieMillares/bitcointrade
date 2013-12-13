package com.bitcointrade.action;

import com.bitcointrade.model.User;
import com.bitcointrade.service.LoginValidator;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.commons.lang3.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: Augie
 * Date: 11/19/13
 * Time: 10:15 PM
 * <p/>
 * Modification:
 * ----------------------------
 */


public class LoginAction extends ActionSupport implements ModelDriven<User> {
    User user = new User();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void validate() {
        if (StringUtils.isEmpty(user.getUserId())) {
            addFieldError("userId", "User ID cannot be blank");
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            addFieldError("password", "Password cannot be blank");
        }
    }

    @Override
    public String execute() {
        if (new LoginValidator().validate(user)) return SUCCESS;
        addActionError("Not Validated");
        return LOGIN;
    }

    @Override
    public User getModel() {
        return user;
    }
}
