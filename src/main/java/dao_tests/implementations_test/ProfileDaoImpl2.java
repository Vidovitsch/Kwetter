package dao_tests.implementations_test;

import dao_tests.interfaces.IProfileDao;
import domain.Profile;
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
public class ProfileDaoImpl2 implements IProfileDao {

    @PersistenceContext(name = "KwetterPU")
    private EntityManager em;

    public ProfileDaoImpl2() {
    }

    public ProfileDaoImpl2(String persistencyUnit) {
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
        try {
            return (Profile) q.getResultList().get(0);
        }catch (ArrayIndexOutOfBoundsException e){
            return null;
        }
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