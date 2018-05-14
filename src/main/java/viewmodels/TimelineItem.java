package viewmodels;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import rest.resources.KweetResource;
import rest.resources.UserResource;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.List;

public class TimelineItem {

    @InjectLink(
            value = "/kweet/{kweetId}",
            method = "GET",
            style = InjectLink.Style.ABSOLUTE,
            bindings = @Binding(name = "kweetId", value = "${instance.kweetId}")
    )
    @XmlElement(name="self")
    Link self;
    private Long kweetId;
    private Date postDate;
    private String message;
    private String username;
    private String profileName;
    private String profileImage;
    private List<UserUsernameView> hearts;
    private boolean ownKweet;
    private List<UserUsernameView> mentions;

    public Long getKweetId() {
        return kweetId;
    }

    public void setKweetId(Long kweetId) {
        this.kweetId = kweetId;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileName() {return profileName;}

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public List<UserUsernameView> getHearts() {
        return hearts;
    }

    public void setHearts(List<UserUsernameView> hearts) {
        this.hearts = hearts;
    }

    public boolean isOwnKweet() {
        return ownKweet;
    }

    public void setOwnKweet(boolean ownKweet) {
        this.ownKweet = ownKweet;
    }

    public List<UserUsernameView> getMentions() {
        return mentions;
    }

    public void setMentions(List<UserUsernameView> mentions) {
        this.mentions = mentions;
    }
}
