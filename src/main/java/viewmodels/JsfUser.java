package viewmodels;

public class JsfUser {
    private long id;
    private String username;
    private String roles;

    public JsfUser(long id, String username, String roles) {
        this.id = id;
        this.username = username;
        this.roles = roles;
    }

    public JsfUser() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
