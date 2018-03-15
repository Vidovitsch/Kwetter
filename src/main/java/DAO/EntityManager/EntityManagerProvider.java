package DAO.EntityManager;

import Qualifier.Testing;
import javax.inject.Inject;
import javax.persistence.EntityManager;

public class EntityManagerProvider {

    @Inject @Testing
    private IEntityManagerImplementation emImplementation;

    public EntityManager GetEntityManager(){
        return emImplementation.GetEntityManger();
    }

}
