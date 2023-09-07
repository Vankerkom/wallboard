package be.vankerkom.messagewall.messages;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessagesController {

    private final MessagesFacade messagesFacade;

    @GetMapping
    public List<Message> getMessages() {
        return this.messagesFacade.findAll();
    }

    @PostMapping
    public Message createMessage(@RequestBody Message message) {
        return this.messagesFacade.createMessage(message);
    }

}
