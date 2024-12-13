package ns.maintainusers.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import ns.maintainusers.controller.error.NoUserFoundException;
import ns.maintainusers.controller.error.UserNameExistsException;
import ns.maintainusers.entity.User;
import ns.maintainusers.service.UserService;


@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {
	
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(value="/users", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<User> getAllUsers() {
		return this.userService.getAllUsers();
	}

	@PostMapping(value="/users")
	@ResponseStatus(HttpStatus.CREATED)
	public User createUser(@RequestBody User newUser) throws UserNameExistsException {
		return this.userService.createUser(newUser);
	}	

	@PutMapping(value="/users/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateUser(@PathVariable Long userId, @RequestBody User updatedUser) throws NoUserFoundException {
		this.userService.updateUser(userId, updatedUser);
	}	

	@DeleteMapping(value="/users/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable Long userId) throws NoUserFoundException {
		this.userService.deleteUser(userId);
	}
}
