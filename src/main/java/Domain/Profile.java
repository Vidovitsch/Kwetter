package Domain;

import javax.persistence.*;

@Entity(name = "Post")
//@Table(name = "Post")
public class Profile {

    //region Fields
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //@Column(name = "Name", nullable = false)
    private String name;
    //@Column(name = "Image")
    private String image;
    //@Column(name = "Biography")
    private String biography;
    //@Column(name = "Location")
    private String location;
    //@Column(name = "Website")
    private String website;
    //endregion

    public Profile() { }

    public Profile(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
