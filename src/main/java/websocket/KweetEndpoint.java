package websocket;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
@ServerEndpoint(value = "/websocket/kweet/{username}")
public class KweetEndpoint {

    private SessionHandler sessionHandler = SessionHandler.getInstance();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        Logger.getAnonymousLogger().log(Level.INFO, username + " connected");
        this.sessionHandler.addSession(session, username);
    }

    @OnClose
    public void onClose(Session session) {
        Logger.getAnonymousLogger().log(Level.INFO, "client disconnected");
        this.sessionHandler.removeSession(session);
    }

    @OnError
    public void onError(Throwable ex) {
        Logger.getAnonymousLogger().log(Level.SEVERE, ex.getMessage());
    }
}
