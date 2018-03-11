package DAO.Impl;

import DaoInterfaces.IKweetDao;
import Domain.Kweet;
import Domain.User;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import java.util.List;

public class KweetDaoImpl implements IKweetDao {

    @Override
    public List<Kweet> findAll() {
        return null;
    }

    @Override
    public Kweet findById(Long id) {
        return null;
    }

    @Override
    public List<Kweet> findByTerm(String message) {
        return null;
    }

    @Override
    public List<Kweet> findBySenderName(String senderName) {
        return null;
    }

    @Override
    public Kweet create(Kweet Kweet) {
        return null;
    }

    @Override
    public List<Kweet> create(List<Kweet> kweets) {
        return null;
    }

    @Override
    public Kweet update(Kweet Kweet) {
        return null;
    }

    @Override
    public boolean remove(Kweet Kweet) {
        return false;
    }
}
