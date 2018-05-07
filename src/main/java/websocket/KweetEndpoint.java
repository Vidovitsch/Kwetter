package websocket;


import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint("/kweet")
public class KweetEndpoint {

    private static Set<Session> clients = Collections.synchronizedSet(new HashSet<>());

    @OnMessage
    public String onMessage(String message) {
        return null;
    }

    @OnOpen
    public void onOpen (Session client) {
        clients.add(client);
    }

    @OnClose
    public void onClose (Session client) {
        clients.remove(client);
    }

    @OnError
    public void onError(Throwable e) {
        e.printStackTrace();
    }
}
