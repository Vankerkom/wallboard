package be.vankerkom.messagewall.events;

import be.vankerkom.messagewall.messages.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageGatewayFacade extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("New WebSocket connection from: {}", session.getRemoteAddress());
        super.afterConnectionEstablished(session);
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        log.info("Connection closed from: {}", session.getRemoteAddress());
    }

    @SneakyThrows
    public void broadcast(Message message) {
        final var payload = this.objectMapper.writeValueAsString(message);
        final var textMessage = new TextMessage(payload);
        this.broadcast(textMessage);
    }

    private void broadcast(TextMessage message) {
        sessions.forEach(session -> sendTo(session, message));
    }

    private final Object sendLock = new Object();

    private void sendTo(WebSocketSession session, TextMessage message) {
        synchronized (sendLock) { // FIXME: Meh, not important for prototype.
            try {
                session.sendMessage(message);
            } catch (IOException e) {
                log.trace("Cannot send message to session: {}", session, e);
            }
        }
    }
}
