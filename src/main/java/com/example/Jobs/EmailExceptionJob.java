package com.example.Jobs;

import com.example.Utils.EmailUtils;
import com.example.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sigal on 30/09/2016.
 */

@Component
public class EmailExceptionJob {
    @Autowired
    private EmailUtils emailUtils;

    @Autowired
    private Utils utils;


    @Scheduled(cron="0 00 22 * * *")
    public void execute() {
        new Thread(){
            public void run(){
                try{

//                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
//                    String date = sdf.format(new Date());
//                    fileName = "dump-" + date + ".sql";
//                    createDump();
//                    emailUtils.sendEmailWithAttachment("c:/dumps/" + fileName, fileName, "Backup: " + date, "Backup: " + date, "Pais","shaigivati464@gmail.com");
                } catch (Exception e) {
//                    utils.saveExceptionObject(this, e);
                }
            }
        }.start();
    }


}
