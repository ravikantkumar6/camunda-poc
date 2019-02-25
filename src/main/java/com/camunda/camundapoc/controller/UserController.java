package com.camunda.camundapoc.controller;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController("")
public class UserController {

    @Autowired
    private ProcessEngine processEngine;

    @GetMapping("/get")
    public void getUserData() {
        System.out.println("***Method invoked****");

        startProcessInstance();
    }

    private ProcessInstance startProcessInstance() {
        Map<String, Object> varMap = new HashMap<>();
        varMap.put(Constants.VAR_NAME_VALUE, UUID.randomUUID().toString());
        varMap.put(Constants.VAR_NAME_NUMBER, null);
        ProcessInstance processInstance =
                processEngine.getRuntimeService().startProcessInstanceByKey("getUserInformation",varMap);
        return processInstance;
    }
}
