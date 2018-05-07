package services;

import com.google.gson.Gson;
import dao.interfaces.IHashtagDao;
import dao.interfaces.IKweetDao;
import dao.interfaces.IProfileDao;
import dao.interfaces.IUserDao;
import domain.Kweet;
import domain.User;
import util.KweetConverter;
import websocket.SessionHandler;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named(value = "KweetBroadcastService")
@Stateless
public class KweetBroadcastService {

    @Inject
    private IProfileDao profileDao;

    private SessionHandler sessionHandler = SessionHandler.getInstance();
    private Gson gson = new Gson();

    public void broadcastKweet(Kweet kweet) {
        User sender = kweet.getSender();
        List<Session> sessions =  getFollowerSessions(sender);
        Session ownSession = sessionHandler.getSession(sender.getUsername());
        if (ownSession != null) {
            sessions.add(ownSession);
        }
        try {
            for (Session session : sessions) {
                session.getBasicRemote().sendText(gson.toJson(KweetConverter.toTimelineItem(kweet, false, profileDao)));
            }
        } catch (IOException ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, ex.getMessage());
        }
    }

    private List<Session> getFollowerSessions(User user) {
        List<Session> sessions = new ArrayList<>();
        for (User follower : user.getFollowers()) {
            Session session = sessionHandler.getSession(follower.getUsername());
            if (session != null) {
                sessions.add(session);
            }
        }
        return sessions;
    }
}
