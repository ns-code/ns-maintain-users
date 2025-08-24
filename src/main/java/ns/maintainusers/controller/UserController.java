package ns.maintainusers.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import ns.maintainusers.controller.error.ErrorMsg;
import ns.maintainusers.controller.error.NoUserFoundException;
import ns.maintainusers.controller.error.UserNameExistsException;
import ns.maintainusers.entity.User;
import ns.maintainusers.service.UserService;


@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {
	
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@Operation(summary = "Get all users", description = "Retrieves a list of all users in the system")
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<User> getAllUsers() {
		return this.userService.getAllUsers();
	}

	@Operation(summary = "Create a new user", description = "Creates a new user with the provided information")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public User createUser(@RequestBody User newUser) throws UserNameExistsException {
		return this.userService.createUser(newUser);
	}	

    @Operation(summary = "Update user", description = "Updates an existing user with the specified ID")
	@PutMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateUser(@PathVariable Long userId, @RequestBody User updatedUser) throws NoUserFoundException {
		this.userService.updateUser(userId, updatedUser);
	}	

	@Operation(summary = "Delete user", description = "Deletes the user with the specified ID")
	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable Long userId) throws NoUserFoundException {
		this.userService.deleteUser(userId);
	}

    @ExceptionHandler(UserNameExistsException.class)
    public ResponseEntity<ErrorMsg> handleUserNameExistsException(UserNameExistsException ex) {
        return new ResponseEntity<>(new ErrorMsg(ex.getMessage()), HttpStatus.CONFLICT);
    }
	
    @ExceptionHandler(NoUserFoundException.class)
    public ResponseEntity<ErrorMsg> handleUserNameExistsException(NoUserFoundException ex) {
        return new ResponseEntity<>(new ErrorMsg(ex.getMessage()), HttpStatus.NOT_FOUND);
    }	
}
