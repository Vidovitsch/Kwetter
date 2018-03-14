package ViewModels;

public class UserImageView {
    String image;
    String username;

    public UserImageView(String username, String image) {
        this.image = image;
        this.username = username;
    }

    public UserImageView() {

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
