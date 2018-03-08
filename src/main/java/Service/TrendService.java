package Service;

import DaoInterfaces.IKweetDao;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class TrendService {

    @EJB
    private IKweetDao kweetDao;

    public TrendService() { }
}
