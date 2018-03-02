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
        return roles;
    }

    public Role findById(long id) {
        for (Role r : roles){
            if(r.getId() == id){return r;};
        }
        return null;
    }

    public Role findByName(String name) {
        for (Role r : roles){
            if(r.getName() == name){return r;};
        }
        return null;
    }

    public Role insertRole(Role role) {
        roles.add(role);
        return role;
    }

    public Role updateRole(Role role) {
        Role r = findById(role.getId());
        if(r == null){
            r = insertRole(role);
        }
        return r;
    }

    public boolean deleteRole(Role role) {
        return roles.remove(role);
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
