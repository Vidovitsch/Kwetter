package Rest.auth;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.SystemException;

@Stateless
public class JWTAuthenticationMechanism implements HttpAuthenticationMechanism {

    private static final String BEARER = "Bearer ";

    @Inject
    private IdentityStore identityStore;

    @Inject
    private JWTStore jwtStore;

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest req, HttpServletResponse res, HttpMessageContext context){

        String authorizationHeader = req.getHeader(AUTHORIZATION);
        Credential credential = null;

        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER)) {
            String token = authorizationHeader.substring(BEARER.length());

            try {
                credential = this.jwtStore.getCredential(token);
            } catch (SystemException e) {
                e.printStackTrace();
            }
        }

        if (credential != null) {
            return context.notifyContainerAboutLogin(this.identityStore.validate(credential));
        } else {
            return context.doNothing();
        }
    }

}