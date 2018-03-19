package dao.implementations_test;

import dao.interfaces.IRoleDao;
import domain.Role;

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
public class RoleDaoImpl2 implements IRoleDao {

    @PersistenceContext(name = "KwetterPU")
    private EntityManager em;

    public RoleDaoImpl2() {
    }

    public RoleDaoImpl2(String persistencyUnit) {
        this.em = Persistence.createEntityManagerFactory(persistencyUnit).createEntityManager();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Role> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Role.class));

        return em.createQuery(cq).getResultList();
    }

    @Override
    public Role findById(Long id) {
        return em.find(Role.class, id);
    }

    @Override
    public Role findByName(String name) {
        Query q = em.createNamedQuery("Role.findByName", Role.class);
        q.setParameter("name", name);

        return (Role) q.getResultList().get(0);
    }

    @Override
    public Role create(Role role) {
        em.getTransaction().begin();
        em.persist(role);
        em.getTransaction().commit();
        return role;
    }

    @Override
    public List<Role> create(List<Role> roles) {
        List<Role> newRoles = new ArrayList<>();
        for (Role role : roles) {
            newRoles.add(create(role));
        }

        return newRoles;
    }

    @Override
    public Role update(Role role) {
        return em.merge(role);
    }

    @Override
    public boolean remove(Role role) {
        em.getTransaction().begin();
        em.remove(role);
        em.getTransaction().commit();
        return true;
    }
}
