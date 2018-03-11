package Rest.Resources;

import DAO.Mock.KweetDaoMock;
import DAO.Mock.UserDaoMock;
import DaoInterfaces.IKweetDao;
import DaoInterfaces.IUserDao;
import Service.ProfileDataService;
import Service.ProfileService;
import Service.TimelineService;
import Service.UserService;
import ViewModels.OtherUserView;
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
import java.util.List;

@Path("user")
@Api(value = "User resource")
public class UserResource {

    @Context
    private UriInfo context;

    public UserResource(){
    }

    @GET
    @Path("/byusername/{username}/following")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve the users a user is following, based on the username", notes = "Username has to be valid")
    public List<OtherUserView> getFollowing(@PathParam("username") String username) {
        UserService userService = new UserService();
        List<OtherUserView> following = userService.getFollowing(username);
        return following;
    }


    @GET
    @Path("/byid/{userid}/following")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve the users a user is following, based on the user-id", notes = "User-id has to be valid")
    public List<OtherUserView> getFollowing(@PathParam("userid") long userid) {
        UserService userService = new UserService();
        List<OtherUserView> following = userService.getFollowing(userid);
        return following;
    }

    @GET
    @Path("/byusername/{username}/followers")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve the users who follow a certain user, based on the username", notes = "Username has to be valid")
    public List<OtherUserView> getFollowers(@PathParam("username") String username) {
        UserService userService = new UserService();
        List<OtherUserView> following = userService.getFollowers(username);
        return following;
    }

    @GET
    @Path("/byid/{userid}/followers")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve the users who follow a certain user,, based on the user-id", notes = "User-id has to be valid")
    public List<OtherUserView> getFollowers(@PathParam("userid") long userid) {
        UserService userService = new UserService();
        List<OtherUserView> following = userService.getFollowers(userid);
        return following;
    }

    @GET
    @Path("/byid/{userid}/profile")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve the profile-data of a certain user, based on the user-id", notes = "User-id has to be valid")
    public ProfileDataView getProfileByUserID(@PathParam("userid") long userid) {
        ProfileDataService profileDataService = new ProfileDataService();
        ProfileDataView profileDataView = profileDataService.GetProfileData(userid);
        return profileDataView;
    }

    @GET
    @Path("/byusername/{username}/profile")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve the profile-data of a certain user, based on the username", notes = "Username has to be valid")
    public ProfileDataView getProfileByUserID(@PathParam("username") String username) {
        ProfileDataService profileDataService = new ProfileDataService();
        ProfileDataView profileDataView = profileDataService.GetProfileData(username);
        return profileDataView;
    }

    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Test REST functionality", notes = "")
    public OtherUserView getTest() {
        OtherUserView userView = new OtherUserView("test", "test");
        return userView;
    }


}
