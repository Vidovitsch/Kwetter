package Rest.Resources;
import Domain.Kweet;
import Service.KweetService;
import Service.TimelineService;
import Util.BooleanResult;
import ViewModels.TimelineItem;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.Set;


@Path("kweet")
@Api(value = "Kweet resource")
public class KweetResource {

    @Context
    private UriInfo context;

    @EJB
    private TimelineService timelineService;

    @EJB
    KweetService kweetService;

    public KweetResource() {
    }

    @GET
    @Path("/last/{amount}/byuserid/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve a users most recent kweets, based on the given amount", notes = "User id needs to be valid and kweets have to be present")
    public Set<TimelineItem> getMostRecentKweetsByID(@PathParam("userid") long userid, @PathParam("amount") int amount) {
        return timelineService.MostRecentKweets(userid, amount);
    }

    @GET
    @Path("/timeline/byuserid/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve the Timeline for a user including his own kweets and kweets from users he is following", notes = "User id has to be valid and kweets have to be available")
    public Set<TimelineItem> getTimelineByUserID(@PathParam("userid") long userid) {
        return timelineService.GenerateTimeLine(userid);
    }

    @GET
    @Path("/mentions/byuserid/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve the Timeline for a user with the Kweets he is mentioned in", notes = "ID has to be a valid user-id")
    public Set<TimelineItem> getMentionsByUserID(@PathParam("userid") long userID) {
        return timelineService.GenerateMentionsTimeLine(userID);
    }


    @GET
    @Path("/last/{amount}/byusername/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve a users most recent kweets, based on the given amount", notes = "Username needs to be valid and kweets have to be present")
    public Set<TimelineItem> getMostRecentKweetsByUsername(@PathParam("username") String username, @PathParam("amount") int amount) {
        return timelineService.MostRecentKweets(username, amount);
    }

    @GET
    @Path("/timeline/byusername/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve the Timeline for a user including his own kweets and kweets from users he is following", notes = "Username has to be valid and kweets have to be available")
    public Set<TimelineItem> getTimelineByUsername(@PathParam("username") String username) {
        return timelineService.GenerateTimeLine(username);
    }

    @GET
    @Path("/mentions/byusername/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve the Timeline for a user with the Kweets he is mentioned in", notes = "Username has to be a valid user-id")
    public Set<TimelineItem> getMentionsByUsername(@PathParam("username") String username) {
        return timelineService.GenerateMentionsTimeLine(username);
    }


    @POST
    @Path("/create/byusername/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve the Timeline for a user with the Kweets he is mentioned in", notes = "Username has to be a valid user-id")
    public BooleanResult publishKweet(@PathParam("username") String username, String message) {
        try{
            kweetService.create(username, message);
            return new BooleanResult("Kweet succesfully posted",true);
        }catch (Exception e){
            return new BooleanResult(e.getMessage(),false);
        }
    }
}
