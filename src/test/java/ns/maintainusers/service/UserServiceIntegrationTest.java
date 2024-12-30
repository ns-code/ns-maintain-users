package ns.maintainusers.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;
import ns.maintainusers.controller.error.UserNameExistsException;
import ns.maintainusers.entity.User;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Slf4j
@DataJpaTest
@Import(UserService.class)
public class UserServiceIntegrationTest {
    @Autowired
    private UserService userService;

    @Test
    void shouldCreateUser() throws Exception {
        User toAddUser = new User(null, "u321", "fname4", "lname4", "email4@test.com", 'I', null);
     
        User savedUser = userService.createUser(toAddUser);

        Assertions.assertTrue(savedUser.getUserId() > 0L);
        Assertions.assertEquals(toAddUser.getUserName(), savedUser.getUserName());
    }

    @Test
    void shouldThowUserNameEsists() throws Exception {
        User toAddUser = new User(null, "u321", "fname4", "lname4", "email4@test.com", 'I', null);
        User toAddSecondUser = new User(null, "u321", "fname5", "lname5", "email5@test.com", 'I', null);
        userService.createUser(toAddUser);     
        Assertions.assertThrows(UserNameExistsException.class, () -> userService.createUser(toAddSecondUser));
    }

}
