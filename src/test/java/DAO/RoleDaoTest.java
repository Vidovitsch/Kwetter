package DAO;

import DAO.Mock.*;
import DaoInterfaces.*;
import Domain.Role;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoTest {

    private static IRoleDao roleDao;
    private static IUserDao userDao;

    @BeforeClass
    public static void Init() {
        userDao = new UserDaoMock();
        roleDao = new RoleDaoMock(userDao.findAll());
    }

    @Test
    public void findAllTest() {
        // Set status before
        List<Role> rolesBefore = new ArrayList<Role>(roleDao.findAll());

        // Insert new role
        Role mockRole = new Role(-1,"mockRole");
        roleDao.insertRole(mockRole);

        // Check status after
        List<Role> rolesAfter = roleDao.findAll();
        Assert.assertEquals("Returns list with size + 1",
                rolesBefore.size() + 1, rolesAfter.size());
        Assert.assertTrue("New Role has been added", rolesAfter.contains(mockRole));

        // Remove mock role (cleanup)
        roleDao.deleteRole(mockRole);
    }

    @Test
    public void findByIdTest() {
        long id = 999999;

        // Insert new role
        Role mockRole = new Role(-1,"mockRole");
        mockRole.setId(id);
        roleDao.insertRole(mockRole);

        // Check fetched role
        Role fetchedRole = roleDao.findById(id);
        Assert.assertEquals("Fetched role is the same as the mocked one", mockRole, fetchedRole);

        // Remove mock role (cleanup)
        roleDao.deleteRole(fetchedRole);
    }

    @Test
    public void findByNameTest() {
        String name = "myRole123";

        // Insert new role
        Role mockRole = new Role(-1,name);
        roleDao.insertRole(mockRole);

        // Check fetched role
        Role fetchedRole = roleDao.findByName(name);
        Assert.assertEquals("Fetched role is the same as the mocked one", mockRole, fetchedRole);

        // Remove mock role (cleanup)
        roleDao.deleteRole(fetchedRole);
    }

    @Test
    public void insertRoleTest() {
        // Insert new role
        Role mockRole = new Role(-1,"mockRole");
        mockRole.setId(999999);
        roleDao.insertRole(mockRole);

        // Check Role list contains new role
        Assert.assertTrue("New role has been added", roleDao.findAll().contains(mockRole));

        // Remove mock role (cleanup)
        roleDao.deleteRole(mockRole);
    }

    @Test
    public void updateRoleTest() {
        String newName = "mockRole123";

        // Insert new role
        Role mockRole = new Role(-1,"mockRole");
        mockRole.setId(999999);
        roleDao.insertRole(mockRole);

        // Update new role
        mockRole.setName(newName);
        roleDao.updateRole(mockRole);

        // Check role list contains new name
        Assert.assertEquals("The name of the role has been changed", newName, mockRole.getName());

        // Remove mock role (cleanup)
        roleDao.deleteRole(mockRole);
    }

    @Test
    public void deleteRoleTest() {
        // Insert new role
        Role mockRole = new Role(-1,"mockRole");
        mockRole.setId(999999);
        roleDao.insertRole(mockRole);

        // Delete inserted role
        roleDao.deleteRole(mockRole);

        // Check Role list contains new role
        Assert.assertFalse("New role has been removed", roleDao.findAll().contains(mockRole));
    }
}
