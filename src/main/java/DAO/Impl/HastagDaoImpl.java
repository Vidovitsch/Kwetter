package DAO.Impl;

import DAO.EntityManager.EntityManagerProvider;
import DaoInterfaces.IHashtagDao;
import Domain.Hashtag;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

@Default
@Stateless
public class HastagDaoImpl implements IHashtagDao {

    private static EntityManager em;

    public HastagDaoImpl() {
        this.em = new EntityManagerProvider().GetEntityManager();
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
        for (Object o : q.getResultList()) {
            return (Hashtag) o;
        }
        return null;
        //return (Hashtag) q.getResultList();
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