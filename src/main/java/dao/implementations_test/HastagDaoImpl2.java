package dao.implementations_test;

import dao.interfaces.IHashtagDao;
import domain.Hashtag;

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
public class HastagDaoImpl2 implements IHashtagDao {

    @PersistenceContext(name = "KwetterPU")
    private EntityManager em;

    public HastagDaoImpl2() { }

    public HastagDaoImpl2(String persistencyUnit) {
        this.em = Persistence.createEntityManagerFactory(persistencyUnit).createEntityManager();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Hashtag> findAll() {

        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Hashtag.class));

        return em.createQuery(cq).getResultList();
    }

    @Override
    public Hashtag findById(Long id) {
        return em.find(Hashtag.class, id);
    }

    @Override
    public Hashtag findByName(String name) {
        Query q = em.createNamedQuery("Hashtag.findByName", Hashtag.class);
        q.setParameter("name", name);
        return (Hashtag) q.getResultList().get(0);
    }

    @Override
    public Hashtag create(Hashtag hashtag) {
        em.getTransaction().begin();
        em.persist(hashtag);
        em.getTransaction().commit();
        return hashtag;
    }

    @Override
    public List<Hashtag> create(List<Hashtag> hashtags) {
        List<Hashtag> newHashtags = new ArrayList<>();
        for (Hashtag hashtag : hashtags) {
            newHashtags.add(create(hashtag));
        }

        return newHashtags;
    }

    @Override
    public Hashtag update(Hashtag hashtag) {
        return em.merge(hashtag);
    }

    @Override
    public boolean remove(Hashtag hashtag) {
        em.getTransaction().begin();
        em.remove(hashtag);
        em.getTransaction().commit();

        return true;
    }
}