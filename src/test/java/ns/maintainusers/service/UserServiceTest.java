package ns.maintainusers.service;

import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import ns.maintainusers.controller.error.UserNameExistsException;
import ns.maintainusers.entity.User;
import ns.maintainusers.repository.UserRepository;

@SpringBootTest
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;    

    @Test
    void shouldReturnAllUsers() {
        User user = new User(1L, "user1");
        List<User> users = new ArrayList<>();
        users.add(user);
        User[] extectedUsers = users.toArray(new User[0]);
        Mockito.when(userRepository.findAll()).thenReturn(users);

        List<User> allUsers = userService.getAllUsers();
        System.out.println(">> allUsers: {}" + allUsers);
        User[] allUsersArray = allUsers.toArray(new User[0]);

        Assertions.assertArrayEquals(extectedUsers, allUsersArray);
    }

    void shouldCreateUser() throws Exception {
        User toAddUser = new User(null, "user1");
        User expectedUser = new User(1L, toAddUser.getName());
        Mockito.when(userRepository.save(toAddUser)).thenReturn(expectedUser);

        User actualUser = userService.createUser(toAddUser);

        Assertions.assertEquals(expectedUser, actualUser);
    }

    void shouldThrowUserNameExistsException() throws Exception {
        User toAddUser = new User(null, "user1");
        User expectedUser = new User(1L, toAddUser.getName());
        Mockito.when(userRepository.save(toAddUser)).thenReturn(expectedUser);

        Assertions.assertThrows(UserNameExistsException.class, () -> userService.createUser(toAddUser));
    }    

    void shouldUpdateUser() throws Exception {
        User toUpdateUser = new User(null, "user1");
        Mockito.doNothing().when(userRepository.save(toUpdateUser));

        verify(userService, times(1)).updateUser(1L, toUpdateUser);
    }    

    void shouldDeleteUser() throws Exception {
        Mockito.doNothing().when(userRepository).deleteById(anyLong());

        verify(userService, times(1)).deleteUser(1L);
    }     
}
