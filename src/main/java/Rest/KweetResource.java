package Rest;

import DAO.Mock.KweetDaoMock;
import DAO.Mock.UserDaoMock;
import DaoInterfaces.IKweetDao;
import Domain.Kweet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Path("kweet")
@Api(value = "Kweet resource")
public class KweetResource {

    @Context
    private UriInfo context;

    IKweetDao kweetDao = new KweetDaoMock((new UserDaoMock()).findAll());


    /**
     * Creates a new instance of KweetResource
     */
    public KweetResource(IKweetDao kweetDao) {
        this.kweetDao = kweetDao;
    }

    /**
     * Get all kweets
     *
     * @return all kweets
     */
    @GET
    @Produces("application/json")
    public Collection<Kweet> getKweets() {
        return kweetDao.findAll();
    }

    @GET
    @Path("{kweetid}")
    @Produces("application/json")
    @ApiOperation(value = "Retrieve a KweetMessage", notes = "Return some kweet as JSON to the client")
    public JsonObject getKweet(@PathParam("kweetid") long kweetid) {
        return Json.createObjectBuilder().add(Long.toString(kweetid), kweetDao.findById(kweetid).getMessage()).build();
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


