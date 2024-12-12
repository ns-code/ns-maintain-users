package ns.maintainusers.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoUserFoundException extends Exception {
    public NoUserFoundException(Long id) {
        super("No user found for id: " + id);
    }
}
