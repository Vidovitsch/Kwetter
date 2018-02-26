package Domain;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity(name = "Role")
//@Table(name = "Role")
public class Role {

    // region Fields

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@Column(name = "ID")
    private long id;

    //@Column(name = "Name", nullable = false)
    private String name;

    //@Column(name = "Description")
    private String description;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users = new HashSet<User>();

    // endregion

    public Role() { }

    public Role(String name) {
        this.name = name;
    }

    // region Getters and Setters

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(HashSet<User> users) {
        this.users = users;
    }

    // endregion
}
