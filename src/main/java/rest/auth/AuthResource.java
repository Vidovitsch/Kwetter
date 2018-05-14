package rest.auth;

import domain.Role;
import domain.User;
import org.json.JSONObject;
import services.AuthenticationService;
import io.swagger.annotations.Api;
import util.BooleanResult;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.SystemException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.security.enterprise.credential.Credential;

@Stateless
@Path("authentication")
@Api(value = "Auth resource")
public class AuthResource {

    @EJB
    private JWTStore jwtStore;

    private static final String BEARER = "Bearer ";
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
            if(credential.getUsername().equals("admin")){roles.add("admin");}

            String token = this.jwtStore.generateToken(u.getUsername(), roles);

            return Response.ok().entity(new BooleanResult("Bearer " + token, true)).header(AUTHORIZATION, "Bearer " + token).build();
        }
        return Response.status(404).build();
    }

    @POST
    @Path(value = "/register")
    public Response register(Credentials credential) {
        boolean succes = authenticationService.registerUser(credential.getUsername(), credential.getPassword());
        if (succes) return Response.ok().entity(new BooleanResult("registered", true)).build();
        return Response.status(404).build();
    }

    @POST
    @Path(value = "/validate")
    public Response validate(BearerWrapper bearer) {
        String token = bearer.Bearer.substring(BEARER.length());
        Credential credentials = null;
        try {
            credentials = this.jwtStore.getCredential(token);
            if(credentials != null){
            return Response.ok().entity(new BooleanResult("valid", true)).header(AUTHORIZATION, "Bearer " + token).build();}
        } catch (SystemException e) {
        }
        return Response.ok().entity(new BooleanResult("invalid", false)).build();

    }
}