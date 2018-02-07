import com.epam.rd.model.ModelCustomer;
import com.epam.rd.model.entity.EntityCustomer;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;


public class ModelCustomerTest {
    ModelCustomer element = new ModelCustomer();


    @Test
    public void createRecord() {
        EntityCustomer s = EntityCustomer.builder()
                .firstname("Name")
                .lastname("LastName")
                .mail("mail")
                .balance(80)
                .password("pass").build();
        s.setPackages(Arrays.asList(16L, 14L));

//		element.create(s);
    }

    @Test
    public void update() {
        element.update(
                EntityCustomer.builder()
                        .id(23L)
                        .firstname("fff")
                        .lastname("ddd")
                        .mail("mmmm")
                        .balance(100)
                        .packages(Arrays.asList(16L))
                        .build()
        );
    }

    @Test
    public void delete() {
        element.delete(
                EntityCustomer.builder()
                        .id(23L)
                        .build()
        );
    }

    @Test
    public void getExistEntity() {
        Assert.assertNotNull(element.loadById(14L));
    }

    @Test
    public void getCollectionEntity() {
        Assert.assertNotNull(element.load());
    }

}
