package Rest.auth;

import javax.security.enterprise.credential.Credential;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JWTCredential implements Credential {

    private String caller;
    private Set<String> groups;
    private Map<String, Serializable> info;

    public JWTCredential(String caller) {
        this(caller, Collections.<String>emptySet());
    }

    public JWTCredential(String caller, Set<String> groups) {
        this.caller = caller;
        this.groups = groups;
        this.info = new HashMap<>();
    }

    public void addInfo(String key, Serializable value) {
        this.info.put(key, value);
    }

    public String getCaller() {
        return caller;
    }

    public Set<String> getGroups() {
        return groups;
    }

    // TODO: consider Generic Type
    // public <T> T getInfo(String key)
    public Serializable getInfo(String key) {
        return info.get(key);
    }
}
