package websocket;

import javax.websocket.Session;
import java.util.*;

public class SessionHandler {

    private static Map<String, Session> sessions = Collections.synchronizedMap(new HashMap<>());

    private static SessionHandler instance = null;

    public static SessionHandler getInstance() {
        if (SessionHandler.instance == null) {
            SessionHandler.instance = new SessionHandler();
        }
        return SessionHandler.instance;
    }
    public void addSession(Session session, String username) {
        SessionHandler.sessions.put(username, session);
    }

    public void removeSession(Session session) {
        String foundUsername = null;
        for (String username : sessions.keySet()) {
            if (this.getSession(username).getId().equals(session.getId())) {
                foundUsername = username;
                break;
            }
        }
        if (foundUsername != null) {
            SessionHandler.sessions.remove(foundUsername);
        }
    }

    public Session getSession(String username) {
        return SessionHandler.sessions.get(username);
    }
}
