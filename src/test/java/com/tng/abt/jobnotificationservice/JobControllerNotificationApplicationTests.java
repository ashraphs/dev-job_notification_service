package com.tng.abt.jobnotificationservice;

import com.tng.abt.jobnotificationservice.jobs.jobprocess.InitialJobProcess;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@SpringBootTest(classes = JobNotificationServiceApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
public class JobControllerNotificationApplicationTests {

    @Autowired
    private InitialJobProcess initialJobProcess;

    Map<String, String> map = new HashMap<>();

}
