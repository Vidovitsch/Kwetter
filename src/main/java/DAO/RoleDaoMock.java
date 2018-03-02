package DAO;

import DaoInterfaces.IRoleDao;
import Domain.Role;
import Domain.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RoleDaoMock implements IRoleDao{

    Collection<Role> roles;

    public RoleDaoMock(Collection<User> users) {
        this.roles = createDummyRoles(users);
    }

    public Collection<Role> findAll() {
        return null;
    }

    public Role findById() {
        return null;
    }

    public Collection<Role> findByName() {
        return null;
    }

    public Role insertRole(Role Role) {
        return null;
    }

    public Role updateRole(Role Role) {
        return null;
    }

    public boolean deleteRole(Role Role) {
        return false;
    }

    private ArrayList<Role> createDummyRoles(Collection<User> users) {
        ArrayList<Role> roles = new ArrayList<Role>();

        Role role1 = new Role("Kweeter");
        Role role2 = new Role("Moderator");
        roles.add(role1);
        roles.add(role2);

        for (User user : users) {
            user.setRoles(roles);
        }
        role1.setUsers(users);
        role2.setUsers(users);

        return roles;
    }
}
