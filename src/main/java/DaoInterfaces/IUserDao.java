package DaoInterfaces;

import Domain.User;
import java.util.List;

public interface IUserDao {

    List<User> findAll();

    User findById(Long id);

    User findByUsername(String username);

    User create(User user);

    User update(User user);

    boolean remove(User user);
}
