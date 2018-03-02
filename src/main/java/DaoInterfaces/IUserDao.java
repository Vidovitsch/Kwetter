package DaoInterfaces;

import Domain.User;

import java.util.List;

public interface IUserDao {
    List<User> findAll();
    User findById();
    List<User> findByName();
    User insertUser(User user);
    User updateUser(User user);
    boolean deleteUser(User user);
}
