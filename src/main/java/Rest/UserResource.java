package Rest;

import DAO.Mock.KweetDaoMock;
import DAO.Mock.UserDaoMock;
import DaoInterfaces.IKweetDao;
import DaoInterfaces.IUserDao;
import Service.ProfileDataService;
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
import java.util.Set;


@Path("user")
@Api(value = "User resource")
public class UserResource {

    @Context
    private UriInfo context;
    IUserDao userDao = new UserDaoMock();
    IKweetDao kweetDao = new KweetDaoMock(userDao.findAll());


    /**
     * Creates a new instance of KweetResource
     */
    public UserResource(IKweetDao kweetDao) {
        this.kweetDao = kweetDao;
    }

    public UserResource(){
    }

    @GET
    @Path("/byusername/{username}/following")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve a users 10 most recent kweets", notes = "User id has to be valid and kweets have to be available")
    public List<OtherUserView> getFollowing(@PathParam("username") String username) {
        UserService userService = new UserService();
        List<OtherUserView> following = userService.getFollowing(username);
        return following;
    }

    @GET
    @Path("/byid/{userid}/following")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve a users 10 most recent kweets", notes = "User id has to be valid and kweets have to be available")
    public List<OtherUserView> getFollowing(@PathParam("userid") long userid) {
        UserService userService = new UserService();
        List<OtherUserView> following = userService.getFollowing(userid);
        return following;
    }

}
