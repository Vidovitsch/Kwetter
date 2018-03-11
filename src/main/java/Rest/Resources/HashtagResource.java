package Rest.Resources;

import Service.TimelineService;
import Service.TrendService;
import ViewModels.TimelineItem;
import ViewModels.TrendView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Path("hashtag")
@Api(value = "Hashtag resource")
public class HashtagResource {

    @Context
    private UriInfo context;

    public HashtagResource(){
    }

    @GET
    @Path("/trends")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve the trends for the current week", notes = "")
    public List<TrendView> getTrends() {
        TrendService trendService = new TrendService();
        ArrayList<TrendView> trends = new ArrayList<>();
        for(String trend : trendService.get()){
            trends.add(new TrendView(trend));
        }
        return trends;
    }

}
