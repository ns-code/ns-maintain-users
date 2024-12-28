package ns.maintainusers.stepdefinitions;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.cucumber.spring.CucumberContextConfiguration;
import jakarta.annotation.PostConstruct;

// @ActiveProfiles("dev")
@CucumberContextConfiguration
@SpringBootTest
public class CucumberSpringConfiguration {


}
