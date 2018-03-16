package DAO.Impl;

import DAO.EntityManager.EntityManagerProvider;
import DaoInterfaces.IRoleDao;
import Domain.Role;
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
public class RoleDaoImpl implements IRoleDao {

    private static EntityManager em;

    public RoleDaoImpl() {
        this.em = new EntityManagerProvider().GetEntityManager();
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

        return (Role) q.getResultList();
    }

    @Override
    public Role create(Role role) {
        em.persist(role);

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
        em.remove(role);

        return true;
    }
}
