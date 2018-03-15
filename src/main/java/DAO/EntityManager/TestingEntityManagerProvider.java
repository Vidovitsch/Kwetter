package DAO.EntityManager;

import Qualifier.Testing;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

@Testing
public class TestingEntityManagerProvider implements IEntityManagerImplementation {

    public static EntityManager em;
    private static EntityManagerFactory factory;

    public TestingEntityManagerProvider() {
        factory = Persistence.createEntityManagerFactory("KWETTERPUP");
        em = factory.createEntityManager();
    }

    @Override
    public EntityManager GetEntityManger() {
        return em;
    }
}
