package DAO.Impl;

import DaoInterfaces.IKweetDao;
import Domain.Kweet;
import Domain.User;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class KweetDaoImpl implements IKweetDao {

    public List<Kweet> findAll() {
        return null;
    }

    public Kweet findById(long id) {
        return null;
    }

    public List<Kweet> findByTerm(String message) {
        return null;
    }

    public List<Kweet> findBySenderName(String senderName) {
        return null;
    }

    public Kweet create(Kweet Kweet) {
        return null;
    }

    public Kweet update(Kweet Kweet) {
        return null;
    }

    public boolean remove(Kweet Kweet) {
        return false;
    }

    public List<Kweet> getTimeline(User user) {
        return null;
    }

    public List<Kweet> search(String term) {
        return null;
    }
}
