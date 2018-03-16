package DAO.Impl;

import DaoInterfaces.IProfileDao;
import Domain.Profile;
import Domain.User;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

@Default
@Stateless
public class ProfileDaoImpl implements IProfileDao {

    @PersistenceContext(name = "KwetterPU")
    private EntityManager em;

    public ProfileDaoImpl() {
    }

    public ProfileDaoImpl(String persistencyUnit) {
        this.em = Persistence.createEntityManagerFactory(persistencyUnit).createEntityManager();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Profile> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Profile.class));

        return em.createQuery(cq).getResultList();
    }

    @Override
    public Profile findById(Long id) {
        return em.find(Profile.class, id);
    }

    @Override
    public Profile findByUser(User user) {
        Query q = em.createNamedQuery("Profile.findByUser", Profile.class);
        q.setParameter("user", user);
        return (Profile) q.getResultList().get(0);
    }

    @Override
    public Profile create(Profile profile) {
        em.getTransaction().begin();
        em.persist(profile);
        em.getTransaction().commit();
        return profile;
    }

    @Override
    public List<Profile> create(List<Profile> profiles) {
        List<Profile> newProfiles = new ArrayList<>();
        for (Profile profile : profiles) {
            newProfiles.add(create(profile));
        }

        return newProfiles;
    }

    @Override
    public Profile update(Profile profile) {
        return em.merge(profile);
    }

    @Override
    public boolean remove(Profile profile) {
        em.getTransaction().begin();
        em.remove(profile);
        em.getTransaction().commit();
        return true;
    }
}