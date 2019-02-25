package com.camunda.camundapoc.bpmn;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class UserAddressInformation implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
System.out.println("Got the User Address Information");
    }
}
