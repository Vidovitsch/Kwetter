package Rest.Resources;

import Exception.InvalidProfileException;
import Service.ProfileService;
import Service.UserService;
import Util.BooleanResult;
import ViewModels.OtherUserView;
import ViewModels.ProfileData;
import ViewModels.UserTotalsView;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("user")
@Api(value = "User resource")
public class UserResource {

    @Context
    private UriInfo context;

    @EJB
    private UserService userService;

    @EJB
    private ProfileService profileService;

    public UserResource() { }

    @GET
    @Path("/{username}/following")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve the users a user is following, based on the username", notes = "Username has to be valid")
    public List<OtherUserView> getFollowing(@PathParam("username") String username) {

        return userService.getFollowing(username);
    }

    @GET
    @Path("/{username}/followers")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve the users who follow a certain user, based on the username", notes = "Username has to be valid")
    public List<OtherUserView> getFollowers(@PathParam("username") String username) {

        return userService.getFollowers(username);
    }

    @GET
    @Path("/{username}/profile")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve the profile-data of a certain user, based on the username", notes = "Username has to be valid")
    public ProfileData getProfileByUserID(@PathParam("username") String username) {

        return profileService.getProfileData(username);
    }

    @GET
    @Path("/byusername/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve a users total numbers", notes = "Username has to be valid and only the available data will be given as a result")
    public UserTotalsView getUserTotals(@PathParam("username") String username) {

        return profileService.getUserTotals(username);
    }

    @POST
    @Path("/profile/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Create a new profile for a user, identified by the user's id", notes = "Userid has to be a valid user-id")
    public BooleanResult setProfile(@PathParam("username") String username, ProfileData profileData) {
        try {
            profileService.setProfile(username, profileData);
        } catch (EJBException e) {
            return new BooleanResult(e.getCausedByException().getMessage(), false);
        } catch (InvalidProfileException e) {
            return new BooleanResult(e.getMessage(), false);
        }
        Gson gsonObj = new Gson();
        String strJson = gsonObj.toJson(profileData);

        return new BooleanResult(strJson, true);
    }

    @POST
    @Path("/{username}/following/add/{following}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Add user to follow to a user, both identified by the user's id's", notes = "Userid's have to be a valid")
    public BooleanResult follow(@PathParam("username") String username, @PathParam("following") String following) {
        boolean added = userService.addFollowing(username, following);
        if (added) {
            return new BooleanResult("User now followed", true);
        } else {
            return new BooleanResult("User could not be followed", false);
        }
    }
}
