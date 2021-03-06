package rest.resources;

import domain.Hashtag;
import services.TrendService;
import viewmodels.TrendView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Path("hashtag")
@Api(value = "Hashtag resource")
public class HashtagResource {

    @Context
    private UriInfo context;

    @EJB
    private TrendService trendService;

    @GET
    @Path("/trends")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve the trends for the current week", notes = "")
    public List<TrendView> getTrends() {
        ArrayList<TrendView> trends = new ArrayList<>();
        for (Hashtag trend : trendService.get()) {
            trends.add(new TrendView(trend.getName(), trend.getLastUsed(), trend.getTimesUsed()));
        }

        return trends;
    }
}
