package DAO.Impl2;

import DaoInterfaces.IUserDao;
import Domain.User;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl2 implements IUserDao {


    private EntityManager em;
    private static EntityManagerFactory factory;

    public UserDaoImpl2() {
        factory = Persistence.createEntityManagerFactory("KWETTERPUP");
        em = factory.createEntityManager();
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
