package com.example.Jobs;

import com.example.Services.GeneralManager;
import com.example.Utils.EmailUtils;
import com.example.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

import java.io.*;
import java.util.Date;

/**
 * Created by Sigal on 25/09/2016.
 */

@Component
public class BackupJob {
    @Autowired
    private GeneralManager generalManager;

    @Autowired
    private EmailUtils emailUtils;

    @Autowired
    private Utils utils;

    private String fileName;

    @Scheduled(cron="0 15 20 * * *")
    public void execute() {
        new Thread(){
            public void run(){
                try{
                } catch (Exception e) {
                }
            }
        }.start();
    }



}
