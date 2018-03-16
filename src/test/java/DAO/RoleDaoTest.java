package DAO;

import DAO.Impl.RoleDaoImpl;
import DaoInterfaces.*;
import Domain.Role;
import Util.MockFactory;
import Util.MockService;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoTest {

    private static IRoleDao roleDao;

    @BeforeClass
    public static void Init() {
        roleDao = new RoleDaoImpl("KwetterPU_test");
    }

    @AfterClass
    public static void tearDown() {
        MockService.renewMockData();
    }

    @Test
    public void findAllTest() {
        // Set status before
        List<Role> rolesBefore = new ArrayList<Role>(roleDao.findAll());

        // Insert new role
        Role mockRole = new Role((long)-1,"mockRole");
        roleDao.create(mockRole);

        // Check status after
        List<Role> rolesAfter = roleDao.findAll();
        Assert.assertEquals("Returns list with size + 1",
                rolesBefore.size() + 1, rolesAfter.size());
        Assert.assertTrue("New Role has been added", rolesAfter.contains(mockRole));

        // Remove mock role (cleanup)
        roleDao.remove(mockRole);
    }

    @Test
    public void findByIdTest() {
        // Insert new role
        Role mockRole = roleDao.create(new Role());

        // Check fetched role
        Role fetchedRole = roleDao.findById(mockRole.getId());
        Assert.assertEquals("Fetched role is the same as the mocked one", mockRole, fetchedRole);

        // Remove mock role (cleanup)
        roleDao.remove(fetchedRole);
    }

    @Test
    public void findByNameTest() {
        String name = "myRole123";

        // Insert new role
        Role mockRole = new Role((long)-1,name);
        roleDao.create(mockRole);

        // Check fetched role
        Role fetchedRole = roleDao.findByName(name);
        Assert.assertEquals("Fetched role is the same as the mocked one", mockRole, fetchedRole);

        // Remove mock role (cleanup)
        roleDao.remove(fetchedRole);
    }

    @Test
    public void insertRoleTest() {
        // Insert new role
        Role mockRole = new Role((long)-1,"mockRole");
        mockRole.setId((long)999999);
        roleDao.create(mockRole);

        // Check Role list contains new role
        Assert.assertTrue("New role has been added", roleDao.findAll().contains(mockRole));

        // Remove mock role (cleanup)
        roleDao.remove(mockRole);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void insertRolesTest() {
        // Insert new role
        List<Role> mockRoles = (List<Role>) MockFactory.createMocks(Role.class, 3);
        roleDao.create(mockRoles);

        // Check Role list contains new role
        Assert.assertTrue("New roles have been added", roleDao.findAll().containsAll(mockRoles));
    }

    @Test
    public void updateRoleTest() {
        String newName = "mockRole123";

        // Insert new role
        Role mockRole = new Role((long)-1,"mockRole");
        mockRole.setId((long)999999);
        roleDao.create(mockRole);

        // Update new role
        mockRole.setName(newName);
        roleDao.update(mockRole);

        // Check role list contains new name
        Assert.assertEquals("The name of the role has been changed", newName, mockRole.getName());

        // Remove mock role (cleanup)
        roleDao.remove(mockRole);
    }

    @Test
    public void deleteRoleTest() {
        // Insert new role
        Role mockRole = new Role((long)-1,"mockRole");
        mockRole.setId((long)999999);
        roleDao.create(mockRole);

        // Delete inserted role
        roleDao.remove(mockRole);

        // Check Role list contains new role
        Assert.assertFalse("New role has been removed", roleDao.findAll().contains(mockRole));
    }
}
