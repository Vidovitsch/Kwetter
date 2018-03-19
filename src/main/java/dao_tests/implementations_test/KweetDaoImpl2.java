package dao_tests.implementations_test;

import dao_tests.interfaces.IKweetDao;
import domain.Kweet;
import domain.User;

import javax.ejb.Stateless;
import javax.enterprise.inject.Alternative;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Alternative
public class KweetDaoImpl2 implements IKweetDao {

    @PersistenceContext(name = "KwetterPU")
    private EntityManager em;

    public KweetDaoImpl2() { }

    public KweetDaoImpl2(String persistencyUnit) {
        this.em = Persistence.createEntityManagerFactory(persistencyUnit).createEntityManager();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Kweet> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Kweet.class));

        return em.createQuery(cq).getResultList();
    }

    @Override
    public Kweet findById(Long id) {
        return em.find(Kweet.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Kweet> findBySender(User sender) {
        Query q = em.createNamedQuery("Kweet.findBySender", Kweet.class);
        q.setParameter("sender", sender);
        return (List<Kweet>) q.getResultList();
    }

    @Override
    public Kweet create(Kweet Kweet) {
        em.getTransaction().begin();
        em.persist(Kweet);
        em.getTransaction().commit();
        return Kweet;
    }

    @Override
    public List<Kweet> create(List<Kweet> kweets) {
        List<Kweet> newKweets = new ArrayList<>();
        for (Kweet kweet : kweets) {
            newKweets.add(create(kweet));
        }

        return newKweets;
    }

    @Override
    public Kweet update(Kweet Kweet) {
        return em.merge(Kweet);
    }

    @Override
    public boolean remove(Kweet Kweet) {
        em.getTransaction().begin();

        em.remove(Kweet);
        em.getTransaction().commit();
        return true;
    }
}
