package Rest.Resources;
import Service.TimelineService;
import ViewModels.TimelineItem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.*;
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
    @Path("/last/{amount}/byuserid/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve a users most recent kweets, based on the given amount", notes = "User id needs to be valid and kweets have to be present")
    public Set<TimelineItem> getMostRecentKweetsByID(@PathParam("userid") long userid, @PathParam("userid") int amount) {
        TimelineService timelineService = new TimelineService();
        return timelineService.MostRecentKweets(userid, amount);
    }

    @GET
    @Path("/timeline/byuserid/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve the Timeline for a user including his own kweets and kweets from users he is following", notes = "User id has to be valid and kweets have to be available")
    public Set<TimelineItem> getTimelineByUserID(@PathParam("userid") long userid) {
        TimelineService timelineService = new TimelineService();
        return timelineService.GenerateTimeLine(userid);
    }

    @GET
    @Path("/mentions/byuserid/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve the Timeline for a user with the Kweets he is mentioned in", notes = "ID has to be a valid user-id")
    public Set<TimelineItem> getMentionsByUserID(@PathParam("userid") long userID) {
        TimelineService timelineService = new TimelineService();
        return timelineService.GenerateMentionsTimeLine(userID);
    }


    @GET
    @Path("/last/{amount}/byusername/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve a users most recent kweets, based on the given amount", notes = "Username needs to be valid and kweets have to be present")
    public Set<TimelineItem> getMostRecentKweetsByUsername(@PathParam("username") String username, @PathParam("amount") int amount) {
        TimelineService timelineService = new TimelineService();
        return timelineService.MostRecentKweets(username, amount);
    }

    @GET
    @Path("/timeline/byusername/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve the Timeline for a user including his own kweets and kweets from users he is following", notes = "Username has to be valid and kweets have to be available")
    public Set<TimelineItem> getTimelineByUsername(@PathParam("username") String username) {
        TimelineService timelineService = new TimelineService();
        return timelineService.GenerateTimeLine(username);
    }

    @GET
    @Path("/mentions/byusername/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve the Timeline for a user with the Kweets he is mentioned in", notes = "Username has to be a valid user-id")
    public Set<TimelineItem> getMentionsByUsername(@PathParam("username") String username) {
        TimelineService timelineService = new TimelineService();
        return timelineService.GenerateMentionsTimeLine(username);
    }

    @POST
    @Path("/mentions/byusername/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve the Timeline for a user with the Kweets he is mentioned in", notes = "Username has to be a valid user-id")
    public Set<TimelineItem> publishKweet(@PathParam("username") String username) {
        TimelineService timelineService = new TimelineService();
        return timelineService.GenerateMentionsTimeLine(username);
    }

}
