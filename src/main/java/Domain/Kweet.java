package Domain;

import java.util.HashSet;

public class Kweet {

    // Unique identifier of this kweet
    private int id;

    private User sender;
    private HashSet<String> mentions;

    public Kweet(User sender) {
        this.sender = sender;
    }

    public Kweet(User sender, HashSet<String> mentions) {
        this.sender = sender;
        this.mentions = mentions;
    }
}
