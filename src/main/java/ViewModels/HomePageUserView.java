package ViewModels;

public class HomePageUserView {

    String image;
    String username;

    public HomePageUserView(String username,String image) {
        this.image = image;
        this.username = username;
    }

    public HomePageUserView() { }

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
