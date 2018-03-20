package rest.auth;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.ejb.Stateless;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import javax.enterprise.inject.Default;
import javax.transaction.SystemException;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
@Stateless
@Default
public class JWTStore {

    private static final Instant CURRENT_TIME = Instant.now();
    private static final Instant EXPIRED_TIME = CURRENT_TIME.plus(3, ChronoUnit.MINUTES);

    private SecretKey secretKey = null;

    public String generateToken(final String username, final List<String> groupNames) throws SystemException {
        try {
            if(secretKey == null){
                setKey();
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

        } catch (JOSEException | NoSuchAlgorithmException e) {
            throw new SystemException(e.getMessage());
        }
    }

    public JWTCredential getCredential(String token) throws SystemException {
        try {
            if(secretKey == null){
                setKey();
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

        } catch (ParseException | JOSEException | NoSuchAlgorithmException e) {
            throw new SystemException(e.getMessage());
        }
    }

    private void setKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        UUID uid = UUID.randomUUID();
        secureRandom.setSeed(uid.toString().getBytes());
        keyGenerator.init(256, secureRandom);
        secretKey = keyGenerator.generateKey();
    }

    protected boolean isTokenTimeValid(final Date creation, final Date expiration) {
        Date now = new Date();

        return creation.before(now) && now.before(expiration);
    }
}
