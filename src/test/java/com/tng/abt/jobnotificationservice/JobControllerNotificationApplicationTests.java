package com.tng.abt.jobnotificationservice;

import com.tng.abt.jobnotificationservice.jobs.jobprocess.InitialJobProcess;
import com.tng.abt.jobnotificationservice.pojos.InitialJobPojo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@Slf4j
@SpringBootTest(classes = JobNotificationServiceApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
public class JobControllerNotificationApplicationTests {

    @Autowired
    private InitialJobProcess initialJobProcess;

    @Test
    public void listOfInitialJobTest() {

        List<InitialJobPojo> test = initialJobProcess.init();
        log.info(">>>>>>>>>> Total Size: {}", String.valueOf(test.size()));
        log.info(">>>>>>>> job name: {}",test.toString());

    }

}
