package be.vankerkom.messagewall.messages;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RequiredArgsConstructor
@Service
@Slf4j
public class MessagesService {

    private final List<Message> messages = new CopyOnWriteArrayList<>();

    public List<Message> findAll() {
        return this.messages;
    }

    public Message store(Message message) {
        final var storedMessage = message.withRandomId();
        this.messages.add(storedMessage);
        log.debug("Saved new message: {}", storedMessage);
        return storedMessage;
    }

}
