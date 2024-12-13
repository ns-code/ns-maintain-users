package ns.maintainusers.service;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import ns.maintainusers.controller.error.NoUserFoundException;
import ns.maintainusers.controller.error.UserNameExistsException;
import ns.maintainusers.entity.User;
import ns.maintainusers.repository.UserRepository;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @Order(1)
    void shouldCreateUser() throws Exception {
        User toAddUser = new User(null, "u321");
        User expectedUser = new User(1L, "u321");
     
        Mockito.when(userRepository.findByName(toAddUser.getName())).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(toAddUser)).thenReturn(expectedUser);

        User actualUser = userService.createUser(toAddUser);

        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    @Order(3)
    void shouldThrowUserNameExistsException() throws Exception {
        User toAddUser = new User(null, "u321");
        User expectedUser = new User(1L, "u321");
        
        Mockito.when(userRepository.findByName(toAddUser.getName())).thenReturn(Optional.of(expectedUser));

        Assertions.assertThrows(UserNameExistsException.class, () -> userService.createUser(toAddUser));
    }    

    @Test
    @Order(4)
    void shouldUpdateUser() throws Exception {
        userService = mock(UserService.class);
        User toUpdateUser = new User(1L, "u321");
        Mockito.when(userRepository.save(toUpdateUser)).thenReturn(toUpdateUser);
        userService.updateUser(1L, toUpdateUser);
        verify(userService, times(1)).updateUser(1L, toUpdateUser);
    }    

    @Test
    @Order(5)
    void shouldDeleteUser() throws Exception {
        userService = mock(UserService.class);
        User expectedUser = new User(1L, "u321");
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(expectedUser));
        userService.deleteUser(1L);
        verify(userService, times(1)).deleteUser(1L);
    }  

    @Test
    @Order(6)
    void shouldThrowNoUserFoundException() throws Exception {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoUserFoundException.class, () -> userService.deleteUser(1L));
    }  

    @Test
    @Order(2)
    void shouldReturnAllUsers() {

        User user = new User(1L, "u321");

        List<User> users = new ArrayList<>();
        users.add(user);
        User[] extectedUsers = users.toArray(new User[0]);
        Mockito.when(userRepository.findAll()).thenReturn(users);

        List<User> allUsers = userService.getAllUsers();
        // System.out.println(">> allUsers: {}" + allUsers);
        User[] allUsersArray = allUsers.toArray(new User[0]);

        Assertions.assertArrayEquals(extectedUsers, allUsersArray);
    }
}
