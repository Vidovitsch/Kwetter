package viewmodels;

public class OtherUserView {
    String username;
    String image;

    public OtherUserView() {
    }

    public OtherUserView(String username, String image) {

        this.username = username;
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
