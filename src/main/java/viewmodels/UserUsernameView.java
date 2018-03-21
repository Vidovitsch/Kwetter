package viewmodels;

public class UserUsernameView {

    private String userName;
    private long id;

    public UserUsernameView() {
    }

    public UserUsernameView(String userName, long id) {

        this.userName = userName;
        this.id = id;
    }

    public String getUserName() {

        return userName;
    }

    public void setDisplayName(String userName) {
        this.userName = userName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
