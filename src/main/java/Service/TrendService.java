package Service;

import DaoInterfaces.IKweetDao;
import Domain.Hashtag;
import Domain.Kweet;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class TrendService {

    @EJB
    private IKweetDao kweetDao;

    public TrendService() { }
}
