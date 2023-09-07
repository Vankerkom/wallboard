package be.vankerkom.messagewall.messages;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public record Message(UUID id, String name, String message) {

    @JsonIgnore
    public boolean isValid() {
        return name != null && !name.isBlank()
                && message != null && !message.isBlank();
    }


    public Message withRandomId() {
        return new Message(
                UUID.randomUUID(),
                name,
                message
        );
    }

}
