package rest.auth;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.SystemException;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Stateless
public class JWTAuthenticationMechanism implements HttpAuthenticationMechanism {

    private static final String BEARER = "Bearer ";

    @Inject
    private IdentityStore identityStore;

    @Inject
    private JWTStore jwtStore;

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest req, HttpServletResponse res, HttpMessageContext context){

        String s = req.getAuthType();
        String authorizationHeader = req.getHeader(AUTHORIZATION);
        String bearerToken = null;
        Cookie[] cookies = req.getCookies();
        if(cookies != null) {
            for (Cookie c : cookies){
                if(c.getName().equals("authorization")){
                    bearerToken = c.getValue();
                    break;
                }
            }
        }
        Credential credential = null;

        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER)) {
            String token = authorizationHeader.substring(BEARER.length());

            try {
                if(!token.equals("testietostie")){
                    credential = this.jwtStore.getCredential(token);
                }
                else{
                    return context.doNothing();
                }

            } catch (SystemException e) {
                e.printStackTrace();
            }
        } else if (bearerToken != null && !bearerToken.equals("")&& bearerToken.startsWith(BEARER)) {
            String token = bearerToken.substring(BEARER.length());
            try {

                credential = this.jwtStore.getCredential(token);
            } catch (SystemException e) {
                res.setStatus(400);
                e.printStackTrace();
                if(req.getRequestURI().contains("authentication/login")){
                    return context.doNothing();
                }
            }
        }

        if (credential != null) {
            return context.notifyContainerAboutLogin(this.identityStore.validate(credential));
        } else {
            //res.setStatus(405);
            if(!req.getRequestURI().contains("login") && !req.getRequestURI().contains("apiee") && !req.getRequestURI().contains("swagger" ) && !req.getRequestURI().contains("authentication" )&& !req.getRequestURI().contains("resource" )){
            return context.redirect("/Kwetter-1.0-SNAPSHOT/login.xhtml");}
            else{return context.doNothing();}
        }
    }

}