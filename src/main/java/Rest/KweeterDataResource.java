package Rest;

import DAO.Mock.KweetDaoMock;
import DAO.Mock.UserDaoMock;
import DaoInterfaces.IKweetDao;
import Service.KweeterDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.JSONException;
import org.json.JSONObject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
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

    @GET
    @Path("{username}")
    @Produces("application/json")
    @ApiOperation(value = "Retrieve a KweetMessage", notes = "Return some kweet as JSON to the client")
    public JsonObject getKweeterData(@PathParam("username") String username) throws JSONException {
        KweeterDataService kweeterDataService = new KweeterDataService();
        JSONObject json = new JSONObject();
        json.put(username, kweeterDataService.getKweeterData(username));
        return (JsonObject) json;
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
