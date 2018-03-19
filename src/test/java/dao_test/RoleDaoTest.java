package dao_test;

import dao.implementations.RoleDaoImpl;
import dao.interfaces.*;
import domain.Role;
import util.MockFactory;
import util.MockService;
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

        beginRoleTransaction();
        roleDao.create(mockRole);
        endRoleTransaction();

        // Check status after
        List<Role> rolesAfter = roleDao.findAll();
        Assert.assertEquals("Returns list with size + 1",
                rolesBefore.size() + 1, rolesAfter.size());
        Assert.assertTrue("New Role has been added", rolesAfter.contains(mockRole));

        // Remove mock role (cleanup)
        beginRoleTransaction();
        roleDao.remove(mockRole);
        endRoleTransaction();
    }

    @Test
    public void findByIdTest() {
        // Insert new role
        Role mockRole = (Role) MockFactory.createMocks(Role.class, 1).get(0);

        beginRoleTransaction();
        mockRole = roleDao.create(mockRole);
        endRoleTransaction();

        // Check fetched role
        Role fetchedRole = roleDao.findById(mockRole.getId());
        Assert.assertEquals("Fetched role is the same as the mocked one", mockRole, fetchedRole);

        // Remove mock role (cleanup)
        beginRoleTransaction();
        roleDao.remove(fetchedRole);
        endRoleTransaction();
    }

    @Test
    public void findByNameTest() {
        String name = "myRole123";

        // Insert new role
        Role mockRole = new Role((long)-1,name);

        beginRoleTransaction();
        roleDao.create(mockRole);
        endRoleTransaction();

        // Check fetched role
        Role fetchedRole = roleDao.findByName(name);
        Assert.assertEquals("Fetched role is the same as the mocked one", mockRole, fetchedRole);

        // Remove mock role (cleanup)
        beginRoleTransaction();
        roleDao.remove(fetchedRole);
        endRoleTransaction();
    }

    @Test
    public void insertRoleTest() {
        // Insert new role
        Role mockRole = new Role((long)-1,"mockRole");
        mockRole.setId((long)999999);

        beginRoleTransaction();
        roleDao.create(mockRole);
        endRoleTransaction();

        // Check Role list contains new role
        Assert.assertTrue("New role has been added", roleDao.findAll().contains(mockRole));

        // Remove mock role (cleanup)
        beginRoleTransaction();
        roleDao.remove(mockRole);
        endRoleTransaction();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void insertRolesTest() {
        // Insert new role
        List<Role> mockRoles = (List<Role>) MockFactory.createMocks(Role.class, 3);

        beginRoleTransaction();
        roleDao.create(mockRoles);
        endRoleTransaction();

        // Check Role list contains new role
        Assert.assertTrue("New roles have been added", roleDao.findAll().containsAll(mockRoles));
    }

    @Test
    public void updateRoleTest() {
        String newName = "mockRole123";

        // Insert new role
        Role mockRole = new Role((long)-1,"mockRole");
        mockRole.setId((long)999999);

        beginRoleTransaction();
        roleDao.create(mockRole);
        endRoleTransaction();

        // Update new role
        mockRole.setName(newName);
        roleDao.update(mockRole);

        // Check role list contains new name
        Assert.assertEquals("The name of the role has been changed", newName, mockRole.getName());

        // Remove mock role (cleanup)
        beginRoleTransaction();
        roleDao.remove(mockRole);
        endRoleTransaction();
    }

    @Test
    public void deleteRoleTest() {
        // Insert new role
        Role mockRole = new Role((long)-1,"mockRole");
        mockRole.setId((long)999999);

        beginRoleTransaction();
        roleDao.create(mockRole);
        endRoleTransaction();

        // Delete inserted role
        beginRoleTransaction();
        roleDao.remove(mockRole);
        endRoleTransaction();

        // Check Role list contains new role
        Assert.assertFalse("New role has been removed", roleDao.findAll().contains(mockRole));
    }

    private void beginRoleTransaction() {
        if (roleDao.getEntityManager() != null) {
            roleDao.getEntityManager().getTransaction().begin();
        }
    }

    private void endRoleTransaction() {
        if (roleDao.getEntityManager() != null) {
            roleDao.getEntityManager().getTransaction().commit();
        }
    }
}
