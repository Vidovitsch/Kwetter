package DaoInterfaces;

import Domain.Role;

import java.util.List;

public interface IRoleDao {
    List<Role> findAll();
    Role findById();
    List<Role> findByName();
    Role insertRole(Role Role);
    Role updateRole(Role Role);
    boolean deleteRole(Role Role);
}
