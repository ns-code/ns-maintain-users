package ns.maintainusers.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.slf4j.Slf4j;
import ns.maintainusers.controller.error.UserNameExistsException;
import ns.maintainusers.entity.User;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
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
     
        Assertions.assertThrows(UserNameExistsException.class, () -> userService.createUser(toAddUser));
    }

}
