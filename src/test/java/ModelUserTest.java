import com.epam.rd.model.ModelUser;
import com.epam.rd.model.entity.EntityUser;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;


public class ModelUserTest {
    private ModelUser sut = new ModelUser();

    @Test
    public void createRecord() {
        EntityUser s = EntityUser.builder()
                .login("Admin")
                .password("password")
                .level(1)
                .build();

        sut.create(s);

        Assert.assertEquals("Admin" ,sut.loadByLogin("Admin").getLogin());
        Assert.assertEquals("password" ,sut.loadByLogin("Admin").getPassword());
        Assert.assertEquals("1" ,sut.loadByLogin("Admin").getLevel().toString());

        EntityUser user = sut.loadByLogin(s.getLogin());
        sut.delete(user);
    }

    @Test
    public void update() {
        EntityUser s = EntityUser.builder()
                .login("Admin")
                .password("password")
                .level(1)
                .build();

        sut.create(s);

        EntityUser user = sut.loadByLogin(s.getLogin());

        sut.update(
                EntityUser.builder()
                        .id(user.getId())
                        .login("NewLogin")
                        .password("pass")
                        .level(2)
                        .build()
        );

        Assert.assertEquals("NewLogin" ,sut.load(user.getId()).getLogin());
        Assert.assertEquals("pass" ,sut.load(user.getId()).getPassword());
        Assert.assertEquals("2" ,sut.load(user.getId()).getLevel().toString());

        sut.delete(user);
    }

    @Test
    public void delete() {
        EntityUser s = EntityUser.builder()
                .login("Administrator")
                .password("password")
                .level(1)
                .build();

        sut.create(s);

        List<EntityUser> users = sut.load();

        EntityUser user = sut.loadByLogin(s.getLogin());
        sut.delete(user);

        List<EntityUser> usersAfterDelete = sut.load();
        Assert.assertEquals(users.size(), usersAfterDelete.size() + 1);
    }

    @Test
    public void getExistEntity() {
        EntityUser s = EntityUser.builder()
                .login("Admin")
                .password("password")
                .level(1)
                .build();

        sut.create(s);
        Assert.assertNotNull(sut.loadByLogin(s.getLogin()));

        sut.delete(s);
    }

    @Test
    public void getCollectionEntity() {
        List<EntityUser> users = sut.load();
        for (int i = 0; i < users.size(); i++) {
            Assert.assertNotNull(users.get(i));
        }
    }
}
