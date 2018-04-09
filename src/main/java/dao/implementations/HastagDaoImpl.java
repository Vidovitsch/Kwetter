package dao.implementations;

import dao.interfaces.IHashtagDao;
import domain.Hashtag;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

@Default
@Stateless
public class HastagDaoImpl implements IHashtagDao {

    @PersistenceContext(unitName = "KwetterPU")
    private EntityManager em;

    public HastagDaoImpl() { }

    public HastagDaoImpl(String persistencyUnit) {
        this.em = Persistence.createEntityManagerFactory(persistencyUnit).createEntityManager();
    }

    public EntityManager getEntityManager() {
        return em;
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
    @SuppressWarnings("unchecked")
    public Hashtag findByName(String name) {
        Query q = em.createNamedQuery("Hashtag.findByName", Hashtag.class);
        q.setParameter("name", name);

        List<Hashtag> hashtags = (List<Hashtag>) q.getResultList();
        if (hashtags == null || hashtags.isEmpty()) {
            return null;
        }
        return hashtags.get(0);
    }

    @Override
    public Hashtag create(Hashtag hashtag) {
        em.persist(hashtag);
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
        em.remove(hashtag);

        return true;
    }
}