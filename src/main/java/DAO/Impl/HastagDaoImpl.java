package DAO.Impl;

import DaoInterfaces.IHashtagDao;
import Domain.Hashtag;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class HastagDaoImpl implements IHashtagDao {

    public List<Hashtag> findAll() {
        return null;
    }

    public Hashtag findById(long id) {
        return null;
    }

    public Hashtag findByName(String name) {
        return null;
    }

    public Hashtag create(Hashtag Hashtag) {
        return null;
    }

    public Hashtag update(Hashtag Hashtag) {
        return null;
    }

    public boolean remove(Hashtag Hashtag) {
        return false;
    }

    public List<Hashtag> getTrend() {
        return null;
    }
}
