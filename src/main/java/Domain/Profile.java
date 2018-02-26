package Domain;

public class Profile {

    // Unique identifier of this profile
    private int id;

    private User user;
    private String profileName;

    // Base64
    private String profileImage;

    private String biography;
    private String location;
    private String website;

    public Profile(User user, String profileName) {
        this.user = user;
        this.profileName = profileName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
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
