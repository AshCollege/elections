package com.example.Services.impl;

import com.example.Persist;
import com.example.Services.UserManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Date;
/**
 * Created by Sigal on 5/16/2016.
 */
public class UserManagerImpl implements UserManager{

    @Autowired
    private Persist persist;


    public boolean checkCredentials (String userName, String password) {
        if (userName != null && password != null) {
            return userName.equals("shai") && password.equals("123");
        }
        return false;
    }

}
