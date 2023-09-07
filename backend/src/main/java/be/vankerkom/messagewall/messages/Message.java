package be.vankerkom.messagewall.messages;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record Message(String name, String message) {

    @JsonIgnore
    public boolean isValid() {
        return name != null && !name.isBlank()
                && message != null && !message.isBlank();
    }

}
