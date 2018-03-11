package Rest;

import DAO.Mock.KweetDaoMock;
import DAO.Mock.UserDaoMock;
import DaoInterfaces.IKweetDao;
import Service.KweeterDataService;
import ViewModels.HomePageUserView;
import ViewModels.KweeterData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.JSONException;
import org.json.JSONObject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;


@Path("kweeterdata")
@Api(value = "Kweeterdata resource")
public class KweeterDataResource {

    @Context
    private UriInfo context;

    IKweetDao kweetDao = new KweetDaoMock((new UserDaoMock()).findAll());


    /**
     * Creates a new instance of KweetResource
     */
    public KweeterDataResource(IKweetDao kweetDao) {
        this.kweetDao = kweetDao;
    }

    public KweeterDataResource(){
    }

    @GET
    @Path("/byusername/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve a KweetMessage", notes = "Return some kweet as JSON to the client")
    public KweeterData getKweeterDataByUsername(@PathParam("username") String username) {
        KweeterDataService kweeterDataService = new KweeterDataService();
        KweeterData k = kweeterDataService.getKweeterData(username);
//        HomePageUserView h = new HomePageUserView();
//        h.setUsername("test");
        return k;
    }

    @GET
    @Path("/byid/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve Kweeters Data for the homepage", notes = "ID has to be a valid usero-id")
    public KweeterData getKweeterDataByID(@PathParam("userid") long userID) {
        KweeterDataService kweeterDataService = new KweeterDataService();
        KweeterData k = kweeterDataService.getKweeterData(userID);
//        HomePageUserView h = new HomePageUserView();
//        h.setUsername("test");
        return k;
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
