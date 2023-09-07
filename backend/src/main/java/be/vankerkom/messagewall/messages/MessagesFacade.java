package be.vankerkom.messagewall.messages;

import be.vankerkom.messagewall.events.MessageGatewayFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessagesFacade {


    private final MessagesService messagesService;
    private final MessageGatewayFacade messageGatewayFacade;

    public List<Message> findAll() {
        return this.messagesService.findAll();
    }

    public Message createMessage(Message message) {
        if (!message.isValid()) {
            throw new InvalidMessageException();
        }

        final var savedMessage = this.messagesService.store(message);
        this.messageGatewayFacade.broadcast(savedMessage);

        return message;
    }

}
