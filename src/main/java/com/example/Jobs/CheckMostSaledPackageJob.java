package com.example.Jobs;

import com.example.Services.GeneralManager;
import com.example.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by Sigal on 9/17/2016.
 */
@Component
public class CheckMostSaledPackageJob {

    @Autowired
    private GeneralManager generalManager;

}
