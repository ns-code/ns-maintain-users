package ns.maintainusers.service;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import lombok.extern.slf4j.Slf4j;
import ns.maintainusers.controller.error.NoUserFoundException;
import ns.maintainusers.controller.error.UserNameExistsException;
import ns.maintainusers.entity.User;
import ns.maintainusers.repository.UserRepository;

@Slf4j
@DataJpaTest
@Import(UserService.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldCreateUser() throws Exception {
        User toAddUser = new User(null, "u321", "fname4", "lname4", "email4@test.com", 'I', null);
        User expectedUser = new User(1L, "u321", "fname4", "lname4", "email4@test.com", 'I', null);
     
        Mockito.when(userRepository.findByUserName(toAddUser.getUserName())).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(toAddUser)).thenReturn(expectedUser);

        User actualUser = userService.createUser(toAddUser);

        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    void shouldThrowUserNameExistsException() throws Exception {
        User toAddUser = new User(null, "u321", "fname4", "lname4", "email4@test.com", 'I', null);
        User expectedUser = new User(1L, "u321", "fname4", "lname4", "email4@test.com", 'I', null);
        
        Mockito.when(userRepository.findByUserName(toAddUser.getUserName())).thenReturn(Optional.of(expectedUser));

        Assertions.assertThrows(UserNameExistsException.class, () -> userService.createUser(toAddUser));
    }    

    @Test
    void shouldUpdateUser() throws Exception {
        userService = mock(UserService.class);
        User toUpdateUser = new User(1L, "u321", "fname4", "lname4", "email4@test.com", 'I', null);
        Mockito.when(userRepository.save(toUpdateUser)).thenReturn(toUpdateUser);
        userService.updateUser(1L, toUpdateUser);
        verify(userService, times(1)).updateUser(1L, toUpdateUser);
    }    

    @Test
    void shouldDeleteUser() throws Exception {
        userService = mock(UserService.class);
        User expectedUser = new User(1L, "u321", "fname4", "lname4", "email4@test.com", 'I', null);
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(expectedUser));
        userService.deleteUser(1L);
        verify(userService, times(1)).deleteUser(1L);
    }  

    @Test
    void shouldThrowNoUserFoundException() throws Exception {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoUserFoundException.class, () -> userService.deleteUser(1L));
    }  

    @Test
    void shouldReturnAllUsers() {

        User user = new User(1L, "u321", "fname4", "lname4", "email4@test.com", 'I', null);

        List<User> users = new ArrayList<>();
        users.add(user);
        User[] extectedUsers = users.toArray(new User[0]);
        Mockito.when(userRepository.findAll()).thenReturn(users);

        List<User> allUsers = userService.getAllUsers();
        User[] allUsersArray = allUsers.toArray(new User[0]);

        Assertions.assertArrayEquals(extectedUsers, allUsersArray);
    }
}
