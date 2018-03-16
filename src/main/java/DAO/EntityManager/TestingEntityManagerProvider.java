package DAO.EntityManager;

import Qualifier.Testing;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

@Testing
@Dependent
public class TestingEntityManagerProvider implements IEntityManagerImplementation {

    @Override
    public EntityManager GetEntityManger() {
        return Persistence.createEntityManagerFactory("KWETTERPUP").createEntityManager();
    }
}
