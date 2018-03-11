package Rest.Resources;

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
import Exception.UserNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;


@Path("homepage")
@Api(value = "Homepage resources")
public class HomepageDataResource {

    @Context
    private UriInfo context;


    public HomepageDataResource(){
    }

    @GET
    @Path("/kweeterdata/byusername/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve a KweetMessage", notes = "Return some kweet as JSON to the client")
    public KweeterData getKweeterDataByUsername(@PathParam("username") String username) {
        try {
            KweeterDataService kweeterDataService = new KweeterDataService();
            KweeterData k = kweeterDataService.getKweeterData(username);

            return k;
        } catch (UserNotFoundException ex) {
            return null; // Even aanpassen
        }
    }


    @GET
    @Path("/kweeterdata/byid/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve Kweeters Data for the homepage", notes = "ID has to be a valid user-id")
    public KweeterData getKweeterDataByID(@PathParam("userid") long userID) {
        try {
            KweeterDataService kweeterDataService = new KweeterDataService();
            KweeterData k = kweeterDataService.getKweeterData(userID);
            return k;
        } catch (UserNotFoundException ex) {
            return null; // Even aanpassen
        }
    }


    @GET
    @Path("/timeline/byid/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve the Timeline for a user", notes = "ID has to be a valid user-id")
    public Set<TimelineItem> getTimeline(@PathParam("userid") long userID) {
        TimelineService timelineService = new TimelineService();
        Set<TimelineItem> timeline = timelineService.GenerateTimeLine(userID);
        return timeline;
    }

    @GET
    @Path("/mentionstimeline/byid/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve the Timeline for a user with the Kweets he is mentioned in", notes = "ID has to be a valid user-id")
    public Set<TimelineItem> getMentionsTimeline(@PathParam("userid") long userID) {
        TimelineService timelineService = new TimelineService();
        Set<TimelineItem> mentionsTimeline = timelineService.GenerateMentionsTimeLine(userID);
        return mentionsTimeline;
    }

    @GET
    @Path("/trends")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve the trends for the current week", notes = "")
    public List<TrendView> getTrends() {
        TrendService trendService = new TrendService();
        ArrayList<TrendView> trends = new ArrayList<>();
        for(String trend : trendService.get()){
            trends.add(new TrendView(trend));
        }
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
