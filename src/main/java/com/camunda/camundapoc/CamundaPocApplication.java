package com.camunda.camundapoc;


import com.camunda.camundapoc.camunda.process.configuration.UserConfiguration;
import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableProcessApplication
public class CamundaPocApplication {

    public static void main(String[] args) {
        SpringApplication.run(CamundaPocApplication.class, args);
        ProcessEngine engine = BpmPlatform.getDefaultProcessEngine();
        UserConfiguration.createDefaultUser(engine);
    }
}
