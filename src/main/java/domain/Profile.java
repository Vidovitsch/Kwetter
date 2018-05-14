package domain;

import com.sun.org.apache.xml.internal.serializer.utils.SerializerMessages_zh_CN;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import rest.resources.KweetResource;
import rest.resources.UserResource;
import util.Mockable;

import javax.persistence.*;
import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;

@Entity(name = "Profile")
@Table(name = "Profile")
@NamedQueries({
        @NamedQuery(name = "Profile.findByUser", query = "SELECT a FROM Profile AS a WHERE a.user = :user")
})
public class Profile implements Mockable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "image")
    private String image;

    @Column(name = "biography")
    private String biography;

    @Column(name = "location")
    private String location;

    @Column(name = "website")
    private String website;

    public Profile() { }

    public Profile(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) { this.user = user; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getwebsite() {
        return website;
    }

    public void setwebsite(String website) {
        this.website = website;
    }
}
