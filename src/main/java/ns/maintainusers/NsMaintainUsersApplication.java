package ns.maintainusers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement 
@SpringBootApplication
public class NsMaintainUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(NsMaintainUsersApplication.class, args);
	}
}
