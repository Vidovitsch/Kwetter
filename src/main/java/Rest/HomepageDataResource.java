package Rest;

import DAO.Mock.KweetDaoMock;
import DAO.Mock.UserDaoMock;
import DaoInterfaces.IKweetDao;
import DaoInterfaces.IUserDao;
import Service.KweeterDataService;
import Service.TimelineService;
import Service.TrendService;
import ViewModels.KweeterData;
import ViewModels.TimelineItem;
import ViewModels.TrendView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import java.util.Collection;
import java.util.List;
import java.util.Set;


@Path("homepage")
@Api(value = "Homepage resources")
public class HomepageDataResource {

    @Context
    private UriInfo context;
    IUserDao userDao = new UserDaoMock();
    IKweetDao kweetDao = new KweetDaoMock(userDao.findAll());


    /**
     * Creates a new instance of KweetResource
     */
    public HomepageDataResource(IKweetDao kweetDao) {
        this.kweetDao = kweetDao;
    }

    public HomepageDataResource(){
    }

    @GET
    @Path("/kweeterdata/byusername/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve a KweetMessage", notes = "Return some kweet as JSON to the client")
    public KweeterData getKweeterDataByUsername(@PathParam("username") String username) {
        KweeterDataService kweeterDataService = new KweeterDataService();
        KweeterData k = kweeterDataService.getKweeterData(username);
//        HomePageUserView h = new HomePageUserView();
//        h.setUsername("test");
        return k;
    }


    @GET
    @Path("/kweeterdata/byid/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve Kweeters Data for the homepage", notes = "ID has to be a valid user-id")
    public KweeterData getKweeterDataByID(@PathParam("userid") long userID) {
        KweeterDataService kweeterDataService = new KweeterDataService();
        KweeterData k = kweeterDataService.getKweeterData(userID);
//        HomePageUserView h = new HomePageUserView();
//        h.setUsername("test");
        return k;
    }


    @GET
    @Path("/timeline/byid/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve the Timeline for a user", notes = "ID has to be a valid user-id")
    public Set<TimelineItem> getTimeline(@PathParam("userid") long userID) {
        TimelineService timelineService = new TimelineService();
        Set<TimelineItem> timeline = timelineService.GenerateTimeLine(userDao.findById(userID));
        return timeline;
    }

    @GET
    @Path("/trends")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve the trends for the current week", notes = "")
    public List<TrendView> getTrends() {
        TrendService trendService = new TrendService();
        List<TrendView> trends = trendService.get();
        return trends;
    }


    /**
     * PUT method for updating or creating an instance of BookResource
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }

}
