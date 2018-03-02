package DaoInterfaces;

import Domain.Role;

import java.util.Collection;

public interface IRoleDao {
    Collection<Role> findAll();
    Role findById(long id);
    Role findByName(String name);
    Role insertRole(Role Role);
    Role updateRole(Role Role);
    boolean deleteRole(Role Role);
}
