package com.camunda.camundapoc.camunda.process.configuration;

import static org.camunda.bpm.engine.authorization.Authorization.ANY;
import static org.camunda.bpm.engine.authorization.Authorization.AUTH_TYPE_GRANT;
import static org.camunda.bpm.engine.authorization.Permissions.ALL;
import static org.camunda.bpm.engine.authorization.Resources.FILTER;

import java.util.HashMap;
import java.util.Map;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.authorization.Authorization;
import org.camunda.bpm.engine.authorization.Groups;
import org.camunda.bpm.engine.authorization.Resource;
import org.camunda.bpm.engine.authorization.Resources;
import org.camunda.bpm.engine.filter.Filter;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;

public class UserConfiguration {

  public static void createDefaultUser(ProcessEngine engine) {
    // and add default user to Camunda to be ready-to-go
    if (engine.getIdentityService().createUserQuery().userId("demo").count() == 0) {
      User user = engine.getIdentityService().newUser("demo");
      user.setFirstName("Demo");
      user.setLastName("Demo");
      user.setPassword("demo");
      user.setEmail("demo@camunda.org");
      engine.getIdentityService().saveUser(user);

      Group group = engine.getIdentityService().newGroup(Groups.CAMUNDA_ADMIN);
      group.setName("Administrators");
      group.setType(Groups.GROUP_TYPE_SYSTEM);
      engine.getIdentityService().saveGroup(group);

      for (Resource resource : Resources.values()) {
        Authorization auth =
            engine.getAuthorizationService().createNewAuthorization(AUTH_TYPE_GRANT);
        auth.setGroupId(Groups.CAMUNDA_ADMIN);
        auth.addPermission(ALL);
        auth.setResourceId(ANY);
        auth.setResource(resource);
        engine.getAuthorizationService().saveAuthorization(auth);
      }
      engine.getIdentityService().createMembership("demo", Groups.CAMUNDA_ADMIN);
    }

    // create default "all tasks" filter
    if (engine.getFilterService().createFilterQuery().filterName("Alle").count() == 0) {

      Map<String, Object> filterProperties = new HashMap<String, Object>();
      filterProperties.put("description", "Alle Aufgaben");
      filterProperties.put("priority", 10);

      Filter filter =
          engine
              .getFilterService()
              .newTaskFilter() //
              .setName("Alle") //
              .setProperties(filterProperties) //
              .setOwner("demo") //
              .setQuery(engine.getTaskService().createTaskQuery());
      engine.getFilterService().saveFilter(filter);

      // and authorize demo user for it
      if (engine
              .getAuthorizationService()
              .createAuthorizationQuery()
              .resourceType(FILTER)
              .resourceId(filter.getId()) //
              .userIdIn("demo")
              .count()
          == 0) {
        Authorization managementGroupFilterRead =
            engine.getAuthorizationService().createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        managementGroupFilterRead.setResource(FILTER);
        managementGroupFilterRead.setResourceId(filter.getId());
        managementGroupFilterRead.addPermission(ALL);
        managementGroupFilterRead.setUserId("demo");
        engine.getAuthorizationService().saveAuthorization(managementGroupFilterRead);
      }
    }
  }
}
