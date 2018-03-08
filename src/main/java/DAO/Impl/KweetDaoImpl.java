package DAO.Impl;

import DaoInterfaces.IKweetDao;
import Domain.Kweet;
import Domain.User;

import java.util.Collection;
import java.util.List;

public class KweetDaoImpl implements IKweetDao {

    public Collection<Kweet> findAll() {
        return null;
    }

    public Kweet findById(long id) {
        return null;
    }

    public List<Kweet> findByMessagePart(String message) {
        return null;
    }

    public List<Kweet> findBySenderName(String senderName) {
        return null;
    }

    public Kweet insertKweet(Kweet Kweet) {
        return null;
    }

    public Kweet updateKweet(Kweet Kweet) {
        return null;
    }

    public boolean deleteKweet(Kweet Kweet) {
        return false;
    }

    public List<Kweet> getTimeline(User user) {
        return null;
    }

    public List<Kweet> search(String term) {
        return null;
    }
}
