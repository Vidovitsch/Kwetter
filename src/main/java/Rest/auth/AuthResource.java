package Rest.auth;
import io.swagger.annotations.Api;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

import java.util.Arrays;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.transaction.SystemException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
@Stateless
@Path("tokens")
@Api(value = "Auth resource")
public class AuthResource {

    @EJB
    private JWTStore jwtStore;

    /**
     *
     * @param credential in json should be {"username": "...", "password": "..."}
     * @return JWT token
     */
    @POST
    public Response authenticate(Credentials credential) throws SystemException {
        // TODO: Should compare user credentials on the database.
        String username = credential.username;
        String password = credential.password;

        // TODO: Groups should retrieve from database based on authenticate user.
        String token = this.jwtStore.generateToken(username, Arrays.asList("admin", "user"));

        return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();
    }
}