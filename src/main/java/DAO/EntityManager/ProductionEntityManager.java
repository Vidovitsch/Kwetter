package DAO.EntityManager;

import Qualifier.Production;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Production
@Dependent
public class ProductionEntityManager implements IEntityManagerImplementation {

    @PersistenceContext(name = "KwetterPU")
    public static EntityManager em;

    @Override
    public EntityManager GetEntityManger() {
        return em;
    }
}
