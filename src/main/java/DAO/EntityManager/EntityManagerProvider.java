package DAO.EntityManager;

import Qualifier.Testing;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Default
public class EntityManagerProvider {

    @Inject
    private @Testing
    IEntityManagerImplementation emImplementation;

    private EntityManager em;

    @PostConstruct
    void init() {
        this.em = emImplementation.GetEntityManger();
    }

    public EntityManager GetEntityManager() {
        return em;
    }

}
