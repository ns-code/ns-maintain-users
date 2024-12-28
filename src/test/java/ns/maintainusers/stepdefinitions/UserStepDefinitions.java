package ns.maintainusers.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import ns.maintainusers.entity.User;
import ns.maintainusers.service.UserService;
import ns.maintainusers.stepdefinitions.util.MockUser;
import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class UserStepDefinitions {

    @Autowired
    private UserService userService;

    User newUser;

    @Given("the userName user1 does not exist")
    public void the_user_name_does_not_exist() {
        Optional<User> user = userService.getAllUsers().stream().filter(u -> u.getUserName().equals("user1")).findAny();
        assertFalse(user.isPresent());
    }
    
    @When("a create user with userName user1 is submitted")
    public void a_create_user_details_are_submitted() throws Exception {
        newUser = userService.createUser(MockUser.getMockUser());
        log.info(">> user: {}", newUser);
    }
    
    @Then("the user should be created successfully")
    public void the_user_should_be_created_successfully() {
        // Write code here that turns the phrase above into concrete actions
        // throw new io.cucumber.java.PendingException();
        assertTrue(newUser != null && newUser.getUserId() > 0);
    }

} 