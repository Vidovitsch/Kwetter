package rest.resources;

import domain.Kweet;
import services.KweetService;
import services.TimelineService;
import util.BooleanResult;
import viewmodels.NewKweetData;
import viewmodels.TimelineItem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import exceptions.*;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("kweet")
@Api(value = "Kweet Resource")
public class KweetResource {

    @Context
    private UriInfo context;

    @EJB
    private TimelineService timelineService;

    @EJB
    private KweetService kweetService;

    @GET
    @Path("/last/{amount}/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve a users most recent kweets, based on the given amount", notes = "Username needs to be valid and kweets have to be present")
    public List<TimelineItem> getMostRecentKweetsByUsername(@PathParam("username") String username, @PathParam("amount") int amount) {

        return timelineService.mostRecentKweets(username, amount);
    }

    @GET
    @Path("/search/{filter}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve kweets based on the given text")
    public List<TimelineItem> searchKweets(@PathParam("filter") String filter) {
        return kweetService.search(filter);

    }

    @GET
    @Path("/timeline/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve the Timeline for a user including his own kweets and kweets from users he is following", notes = "Username has to be valid and kweets have to be available")
    public List<TimelineItem> getTimelineByUsername(@PathParam("username") String username) {

        return timelineService.generateTimeline(username);
    }

    @GET
    @Path("/mentions/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve the Timeline for a user with the Kweets he is mentioned in", notes = "Username has to be a valid user-id")
    public List<TimelineItem> getMentionsByUsername(@PathParam("username") String username) {

        return timelineService.generateMentionsTimeline(username);
    }

    @POST
    @Path("/create/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Post a kweet for a user, identified by the username", notes = "Username has to be valid")
    public BooleanResult publishKweet(@PathParam("username") String username, NewKweetData newKweetData)
            throws InvalidKweetException, UserNotFoundException{
        Kweet k;
        try {
            k = kweetService.create(username, newKweetData.getMessage());
        } catch (EJBException e) {
            return new BooleanResult(e.getCausedByException().getMessage(), false);
        }

        return new BooleanResult(k.getMessage(),true);
    }
}
