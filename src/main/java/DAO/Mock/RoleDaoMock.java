package DAO.Mock;

import DaoInterfaces.IRoleDao;
import Domain.Profile;
import Domain.Role;
import Domain.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RoleDaoMock implements IRoleDao{

    private Collection<Role> dummyRoles;

    public RoleDaoMock(Collection<User> users) {
        this.dummyRoles = createDummyRoles(users);
    }

    public Collection<Role> findAll() {
        return dummyRoles;
    }

    public Role findById(long id) {
        for (Role role : dummyRoles) {
            if (role.getId() == id) {
                return role;
            }
        }

        return null;
    }

    public Role findByName(String name) {
        for (Role role : dummyRoles) {
            if (role.getName().equals(name)) {
                return role;
            }
        }

        return null;
    }

    public Role insertRole(Role role) {
        dummyRoles.add(role);

        return role;
    }

    public Role updateRole(Role role) {
        Role existingRole = findById(role.getId());
        if (existingRole == null) {
            dummyRoles.add(role);
        } else {
            ArrayList<Role> roles = (ArrayList<Role>)dummyRoles;
            roles.set(roles.indexOf(existingRole), role);
        }

        return role;
    }

    public boolean deleteRole(Role role) {
        return dummyRoles.remove(role);
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
