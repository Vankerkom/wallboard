package be.vankerkom.messagewall.messages;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class InvalidMessageException extends RuntimeException {

    public InvalidMessageException() {
        super("Invalid message");
    }

}
