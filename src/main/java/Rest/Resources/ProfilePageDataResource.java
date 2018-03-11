package Rest.Resources;

import DAO.Mock.KweetDaoMock;
import DAO.Mock.UserDaoMock;
import DaoInterfaces.IKweetDao;
import DaoInterfaces.IUserDao;
import Service.KweeterDataService;
import Service.ProfileDataService;
import Service.TimelineService;
import Service.TrendService;
import ViewModels.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Set;


@Path("profilepage")
@Api(value = "Profilepage resources")
public class ProfilePageDataResource {

    @Context
    private UriInfo context;

    @EJB
    TimelineService timelineService;

    @EJB
    ProfileDataService profileDataService;

    public ProfilePageDataResource(){
    }

    @GET
    @Path("/mostrecentkweets/byid/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve a users 10 most recent kweets", notes = "User id has to be valid and kweets have to be available")
    public Set<TimelineItem> getMostRecentKweetsByID(@PathParam("userid") long userid) {
        Set<TimelineItem> mostRecentKweets = timelineService.MostRecentKweets(userid, 10);
        return mostRecentKweets;
    }

    @GET
    @Path("/profiledata/byid/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve a users profile data", notes = "User id has to be valid and only the available data will be given as a result")
    public ProfileDataView getProfileData(@PathParam("userid") long userid) {
        ProfileDataView profileDataView = profileDataService.GetProfileData(userid);
        return profileDataView;
    }

    @GET
    @Path("/usertotals/byid/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve a users total numbers", notes = "User id has to be valid and only the available data will be given as a result")
    public UserTotalsView getUserTotals(@PathParam("userid") long userid) {
        UserTotalsView userTotalsView = profileDataService.GetUserTotals(userid);
        return userTotalsView;
    }
}
