package DaoInterfaces;

import Domain.Role;
import java.util.List;

public interface IRoleDao {

    List<Role> findAll();

    Role findById(Long id);

    Role findByName(String name);

    Role create(Role role);

    List<Role> create(List<Role> roles);

    Role update(Role role);

    boolean remove(Role role);
}
