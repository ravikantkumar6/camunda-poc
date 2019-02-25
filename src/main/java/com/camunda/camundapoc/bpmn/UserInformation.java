package com.camunda.camundapoc.bpmn;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class UserInformation implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("Got UserInformation");
        execution.getVariableNames().forEach(System.out::println);
        execution.getVariableNames().forEach(key -> System.out.println(execution.getVariable(key)));
    }
}
