package Rest.auth;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.ejb.Stateless;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.transaction.SystemException;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
@Stateless
@Default
public class JWTStore {

    private static final Instant CURRENT_TIME = Instant.now();
    private static final Instant EXPIRED_TIME = CURRENT_TIME.plus(3, ChronoUnit.MINUTES);

    //@Inject
    KeyGenerator keyGenerator;

    SecretKey secretKey = null;


    public String generateToken(final String username, final List<String> groupNames) throws SystemException {
        try {
            if(secretKey == null){
                SetKey();
            }

            // Create HMAC signer
            JWSSigner signer = new MACSigner(secretKey);

            // Prepare JWT with claims set
            JWTClaimsSet.Builder claimSet = new JWTClaimsSet.Builder();

            claimSet.issuer("swhp");
            claimSet.subject(username);
            claimSet.audience("JavaEE Soteria JWT"); // your application
            claimSet.issueTime(Date.from(CURRENT_TIME));
            claimSet.expirationTime(Date.from(EXPIRED_TIME));

            JSONArray groupValues = new JSONArray();
            groupValues.addAll(groupNames);

            Map<String, Object> groups = new HashMap<>();
            groups.put("groups", groupValues);

            claimSet.claim("realm_access", groups);

            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimSet.build());

            // apply the HMAC protection
            signedJWT.sign(signer);

            // serialize the compact form
            return signedJWT.serialize();

        } catch (KeyLengthException ex) {
            throw new SystemException(ex.getMessage());
        } catch (JOSEException ex) {
            throw new SystemException(ex.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new SystemException(e.getMessage());
        }
    }

    public JWTCredential getCredential(String token) throws SystemException {
        try {
            if(secretKey == null){
                SetKey();
            }
            SignedJWT signedJWT = SignedJWT.parse(token);

            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

            JWSVerifier verifier = new MACVerifier(secretKey);

            if (!signedJWT.verify(verifier)) {
                throw new SystemException("Not Verified");
            }

            if (!isTokenTimeValid(claimsSet.getIssueTime(), claimsSet.getExpirationTime())) {
                // TODO: Give proper message to clinet / mapping the exception to be able show the message.
                throw new SystemException("Expired Token");
            }

            JSONObject realmAccess = (JSONObject) claimsSet.getClaim("realm_access");
            JSONArray groupArray = (JSONArray) realmAccess.get("groups");

            Set<String> groups = new HashSet<>();
            groupArray.forEach(g -> groups.add(g.toString()));

            return new JWTCredential(claimsSet.getSubject(), groups);

        } catch (ParseException | JOSEException ex) {
            throw new SystemException(ex.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new SystemException(e.getMessage());
        }
    }

    private void SetKey() throws NoSuchAlgorithmException {
        keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed("kwetter".getBytes());
        keyGenerator.init(256, secureRandom);
        secretKey = keyGenerator.generateKey();
    }

    protected boolean isTokenTimeValid(final Date creation, final Date expiration) {
        Date now = new Date();

        return creation.before(now) && now.before(expiration);
    }
}
