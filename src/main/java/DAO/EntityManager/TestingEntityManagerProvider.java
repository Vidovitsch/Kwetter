package DAO.Impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class PayaraEntityManagerProvider {

    @PersistenceContext(name = "KwetterPU")
    public static EntityManager em;

}
