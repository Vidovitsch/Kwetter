package viewmodels;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;

public class UserUsernameView {

    @InjectLink(
            value = "/user/{username}/profile",
            method = "GET",
            style = InjectLink.Style.ABSOLUTE,
            bindings = @Binding(name = "username", value = "${instance.userName}")
    )
    @XmlElement(name="self")
    Link self;
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
