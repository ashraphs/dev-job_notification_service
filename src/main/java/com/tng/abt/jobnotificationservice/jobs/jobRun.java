package com.tng.abt.jobnotificationservice.jobs;

import com.project.dingtalk.robot.send.RobotSendServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class jobRun {

    @Autowired
    private RobotSendServices robotSendServices;

    @Scheduled(cron = "${job.fire.notification.cron}")
    public void run() {

        log.info(">>>>>>>>>>>Start");

        String webhook = "https://oapi.dingtalk.com/robot/send?access_token=56b9d5c9b9b9f468b8b2465471ea5e205be87ffb6242b1300ea12f365965ffc3";
        String content = "";
        String[] atMobiles = new String[0];



        robotSendServices.sendTestMessage(webhook, content, atMobiles);

        String title = "Test ";
        String text = "Hello World !";
        String picUrl = "";
        String messageUrl = "this is test 123";
        robotSendServices.sendLinkMessage(webhook, title, text, picUrl, messageUrl);

        title = "test 123";
        text = "this is test 12345";
        robotSendServices.sendMarkdownMessage(webhook, title, text, atMobiles);

        log.info(">>>>> Done send !!<<<<<");
    }

}
