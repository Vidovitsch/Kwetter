package viewmodels;

import java.util.Date;

public class JsfKweet {

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private Date date;
    private String username;
    private String message;

    public JsfKweet() {
    }

    public JsfKweet(long id, Date date, String username, String message) {
        this.id = id;
        this.date = date;
        this.username = username;
        this.message = message;
    }


    public Date getDate() {

        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
