package dao.implementations;

import dao.interfaces.IKweetDao;
import domain.Kweet;
import domain.User;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

@Default
@Stateless
public class KweetDaoImpl implements IKweetDao {

    @PersistenceContext(unitName = "KwetterPU")
    private EntityManager em;

    public KweetDaoImpl() { }

    public KweetDaoImpl(String persistencyUnit) {
        this.em = Persistence.createEntityManagerFactory(persistencyUnit).createEntityManager();
    }

    public EntityManager getEntityManager() {
        return em;
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
    public Kweet create(Kweet kweet) {
        em.persist(kweet);
        return kweet;
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
    public Kweet update(Kweet kweet) {
        return em.merge(kweet);
    }

    @Override
    public boolean remove(Kweet kweet) {

        em.remove(kweet);
        return true;
    }
}
