package DAO.Impl;

import DAO.EntityManager.EntityManagerProvider;
import DaoInterfaces.IUserDao;
import Domain.User;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

@Default
@Stateless
public class UserDaoImpl implements IUserDao {

    private EntityManager em;

    public UserDaoImpl() {
        this.em = new EntityManagerProvider().GetEntityManager();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(User.class));

        return em.createQuery(cq).getResultList();
    }

    @Override
    public User findById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public User findByUsername(String username) {
        Query q = em.createNamedQuery("User.findByUsername", User.class);
        q.setParameter("username", username);

        return (User) q.getResultList();
    }

    @Override
    public User create(User user) {
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();

        return user;
    }

    @Override
    public List<User> create(List<User> users) {
        List<User> newUsers = new ArrayList<>();
        for (User user : users) {
            newUsers.add(create(user));
        }

        return newUsers;
    }

    @Override
    public User update(User user) {
        return em.merge(user);
    }

    @Override
    public boolean remove(User user) {
        em.getTransaction().begin();
        em.remove(user);
        em.getTransaction().commit();
        return true;
    }
}
