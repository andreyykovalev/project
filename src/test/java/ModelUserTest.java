import com.epam.rd.model.ModelCustomer;
import com.epam.rd.model.ModelUser;
import com.epam.rd.model.entity.EntityUser;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;


public class ModelUserTest {
    private ModelUser element = new ModelUser();

    @Test
    public void createRecord() {
        EntityUser s = EntityUser.builder()
                .login("SoasdfmeodsffBest")
                .password("pas")
                .level(1)
                .build();

        element.create(s);
    }

    @Test
    public void update() {
        element.update(
                EntityUser.builder()
                        .id(5L)
                        .login("login2")
                        .password("password2")
                        .level(2)
                        .build()
        );
    }

    @Test
    public void delete() {
        element.delete(
                EntityUser.builder()
                        .id(6L)
                        .build()
        );
    }

    @Test
    public void getExistEntity() {
        Assert.assertNotNull(element.load(6L));
    }

    @Test
    public void getCollectionEntity() {
        Assert.assertNotNull(element.load());
    }

}
