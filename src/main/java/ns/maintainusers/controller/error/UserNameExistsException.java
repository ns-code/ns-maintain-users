package ns.maintainusers.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserNameExistsException extends Exception {
    public UserNameExistsException(String name) {
        super("User name exists: " + name);
    }
}
