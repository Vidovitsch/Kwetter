package Rest.Resources;

import Exception.InvalidProfileException;
import Service.ProfileDataService;
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
    private ProfileDataService profileDataService;

    public UserResource() {
    }

    @GET
    @Path("/byusername/{username}/following")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve the users a user is following, based on the username", notes = "Username has to be valid")
    public List<OtherUserView> getFollowing(@PathParam("username") String username) {
        List<OtherUserView> following = userService.getFollowing(username);
        return following;
    }


    @GET
    @Path("/byid/{userid}/following")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve the users a user is following, based on the user-id", notes = "User-id has to be valid")
    public List<OtherUserView> getFollowing(@PathParam("userid") long userid) {
        List<OtherUserView> following = userService.getFollowing(userid);
        return following;
    }

    @GET
    @Path("/byusername/{username}/followers")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve the users who follow a certain user, based on the username", notes = "Username has to be valid")
    public List<OtherUserView> getFollowers(@PathParam("username") String username) {
        List<OtherUserView> following = userService.getFollowers(username);
        return following;
    }

    @GET
    @Path("/byid/{userid}/followers")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve the users who follow a certain user,, based on the user-id", notes = "User-id has to be valid")
    public List<OtherUserView> getFollowers(@PathParam("userid") long userid) {
        List<OtherUserView> following = userService.getFollowers(userid);
        return following;
    }

    @GET
    @Path("/byid/{userid}/profile")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve the profile-data of a certain user, based on the user-id", notes = "User-id has to be valid")
    public ProfileData getProfileByUserID(@PathParam("userid") long userid) {
        ProfileData profileData = profileDataService.GetProfileData(userid);
        return profileData;
    }

    @GET
    @Path("/byusername/{username}/profile")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve the profile-data of a certain user, based on the username", notes = "Username has to be valid")
    public ProfileData getProfileByUserID(@PathParam("username") String username) {
        ProfileData profileData = profileDataService.GetProfileData(username);
        return profileData;
    }

    @GET
    @Path("/usertotals/byid/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve a users total numbers", notes = "User id has to be valid and only the available data will be given as a result")
    public UserTotalsView getUserTotals(@PathParam("userid") long userid) {
        UserTotalsView userTotalsView = profileDataService.GetUserTotals(userid);
        return userTotalsView;
    }

    @GET
    @Path("/usertotals/byusername/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve a users total numbers", notes = "Username has to be valid and only the available data will be given as a result")
    public UserTotalsView getUserTotals(@PathParam("username") String username) {
        UserTotalsView userTotalsView = profileDataService.GetUserTotals(username);
        return userTotalsView;
    }

    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Test REST functionality", notes = "")
    public OtherUserView getTest() {
        OtherUserView userView = new OtherUserView("test", "test");
        return userView;
    }

    @POST
    @Path("/profile/byid/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Create a new profile for a user, identified by the user's id", notes = "Userid has to be a valid user-id")
    public BooleanResult createProfile(@PathParam("userid") long userid, ProfileData profileData) {
        ProfileData p = null;
        try {
            p = profileDataService.CreateProfile(userid, profileData);
        } catch (EJBException e) {
            return new BooleanResult(e.getCausedByException().getMessage(), false);
        } catch (InvalidProfileException e) {
            return new BooleanResult(e.getMessage(), false);
        }
        Gson gsonObj = new Gson();
        String strJson = gsonObj.toJson(p);
        return new BooleanResult(strJson, true);
    }

    @POST
    @Path("/profile/update/byid/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Update the profile of a user, identified by the user's id", notes = "Userid has to be a valid user-id")
    public BooleanResult updateProfile(@PathParam("userid") long userid, ProfileData profileData) {
        ProfileData p = null;
        try {
            p = profileDataService.UpdateProfile(userid, profileData);
        } catch (EJBException e) {
            return new BooleanResult(e.getCausedByException().getMessage(), false);
        }
        Gson gsonObj = new Gson();
        String strJson = gsonObj.toJson(p);
        return new BooleanResult(strJson, true);
    }

    @POST
    @Path("/byid/{userid}/following/add/byid/{followuserid}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Add user to follow to a user, both identified by the user's id's", notes = "Userid's have to be a valid")
    public BooleanResult updateProfile(@PathParam("userid") long userid, @PathParam("followuserid") long followuserid) {
        boolean added = userService.AddFollowing(userid, followuserid);
        if (added) {
            return new BooleanResult("User now followed", true);
        } else {
            return new BooleanResult("User could not be followed", false);
        }
    }


}
