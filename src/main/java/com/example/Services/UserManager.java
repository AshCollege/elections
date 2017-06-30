package com.example.Services;


import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Sigal on 5/20/2016.
 */
@Transactional
public interface UserManager {
    public boolean checkCredentials (String userName, String password);

}

