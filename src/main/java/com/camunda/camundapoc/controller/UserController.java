package com.camunda.camundapoc.controller;

import com.camunda.camundapoc.model.UserDetail;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.TypedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLOutput;
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

//        startProcessInstance();
    }

    @PostMapping("/post")
    public void postUserData(@RequestBody UserDetail userDetail) {
        System.out.println("***Method Post Method****");
        startProcessInstance(userDetail);
    }

    @GetMapping("/step1")
    public UserDetail getUserEmailId() {
        System.out.println("***Method Step1 Method****");
        UserDetail userDetail = new UserDetail();
        userDetail.setEmail("abc@gmail.com");
        return userDetail;
    }

    @PostMapping(name = "/step2" ,consumes = MediaType.APPLICATION_JSON_VALUE)
    public void postUserDataFinal(@RequestBody Map<String, String> userDetail) {
        System.out.println("***Method Step2 Method****");
        System.out.println("Email ::: " + userDetail.keySet().toString());
        userDetail.keySet().forEach(key -> System.out.println(userDetail.get(key)));
    }

    private ProcessInstance startProcessInstance(UserDetail userDetail) {
        Map<String, Object> varMap = new HashMap<>();
        varMap.put(Constants.VAR_NAME_VALUE, UUID.randomUUID().toString());
        TypedValue typedTransientObjectValue = Variables.objectValue(userDetail, true).create();
        varMap.put(Constants.VAR_NAME_NUMBER, typedTransientObjectValue);
        ProcessInstance processInstance =
                processEngine.getRuntimeService().startProcessInstanceByKey("testConnector", varMap);
        return processInstance;
    }
}
