package Domain;

import java.util.Base64;

public class Profile {

    private Base64 profileImage;
    private String profileName;
    private String biography;
    private String location;
    private String website;

    public Profile(String profileName) {
        this.profileName = profileName;
    }

    public Base64 getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Base64 profileImage) {
        this.profileImage = profileImage;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
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
