package Rest.Resources;

import DAO.Mock.KweetDaoMock;
import DAO.Mock.UserDaoMock;
import DaoInterfaces.IKweetDao;
import DaoInterfaces.IUserDao;
import Service.ProfileDataService;
import Service.TimelineService;
import ViewModels.ProfileDataView;
import ViewModels.TimelineItem;
import ViewModels.UserTotalsView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.Set;


@Path("kweet")
@Api(value = "kweet resource")
public class KweetResource {

    @Context
    private UriInfo context;

    public KweetResource(){
    }

    @GET
    @Path("/mostrecent/byuserid/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve a users 10 most recent kweets", notes = "User id has to be valid and kweets have to be available")
    public Set<TimelineItem> getMostRecentKweetsByID(@PathParam("userid") long userid) {
        TimelineService timelineService = new TimelineService();
        Set<TimelineItem> mostRecentKweets = timelineService.TenMostRecentKweets(userid);
        return mostRecentKweets;
    }

    @GET
    @Path("/timeline/byuserid/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve a users 10 most recent kweets", notes = "User id has to be valid and kweets have to be available")
    public Set<TimelineItem> getTimelineByUserID(@PathParam("userid") long userid) {
        TimelineService timelineService = new TimelineService();
        Set<TimelineItem> mostRecentKweets = timelineService.GenerateTimeLine(userid);
        return mostRecentKweets;
    }



}
