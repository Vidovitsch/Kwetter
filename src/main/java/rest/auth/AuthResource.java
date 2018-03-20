package rest.auth;

import domain.Role;
import domain.User;
import service_tests.AuthenticationService;
import io.swagger.annotations.Api;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.SystemException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Stateless
@Path("authentication")
@Api(value = "Auth resource")
public class AuthResource {

    @EJB
    private JWTStore jwtStore;

    /**
     * @param credential in json should be {"username": "...", "password": "..."}
     * @return JWT token
     */

    @EJB
    private AuthenticationService authenticationService;

    @POST
    @Path(value = "/login")
    public Response authenticate(Credentials credential) throws SystemException {
        User u = authenticationService.login(credential.getUsername(), credential.getPassword());
        if (u != null) {
            ArrayList<String> roles = new ArrayList<>();
            for (Role r : u.getRoles()) roles.add(r.getName());
            String token = this.jwtStore.generateToken(u.getUsername(), roles);
            return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();
        }
        return Response.status(404).build();
    }

    @POST
    @Path(value = "/register")
    public Response register(Credentials credential) {
        boolean succes = authenticationService.RegisterUser(credential.getUsername(), credential.getPassword());
        if (succes) return Response.status(202).build();
        else return Response.status(404).build();
    }
}