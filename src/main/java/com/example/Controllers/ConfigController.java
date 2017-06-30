package com.example.Controllers;

import com.example.Services.GeneralManager;
import com.example.Utils.TemplateUtils;
import com.example.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


/**
 * Created by Sigal on 23/09/2016.
 */

@Controller
public class ConfigController {

    @Autowired
    private Utils utils;

    @Autowired
    private GeneralManager generalManager;

    @Autowired
    private TemplateUtils templateUtils;


}
