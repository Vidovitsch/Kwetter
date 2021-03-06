package viewmodels;

public class ProfileData {
    private String name;
    private String image;
    private String location;
    private String website;
    private String bio;

    public ProfileData(String name, String image, String location, String website, String bio) {
        this.name = name;
        this.image = image;
        this.location = location;
        this.website = website;
        this.bio = bio;
    }

    public ProfileData() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() { return image; }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
