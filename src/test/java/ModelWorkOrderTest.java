
import com.epam.rd.model.ModelWorkOrder;
import com.epam.rd.model.entity.EntityCustomer;
import com.epam.rd.model.entity.EntityPackage;
import com.epam.rd.model.entity.EntityWorkOrder;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;


public class ModelWorkOrderTest {
    private ModelWorkOrder element = new ModelWorkOrder();

    EntityCustomer customer = EntityCustomer.builder().id(23L).build();
    EntityPackage entityPackage = EntityPackage.builder().id(16L).build();
    Calendar date = Calendar.getInstance();
    long t = date.getTimeInMillis();


    @Test
    public void createRecord() {
        EntityWorkOrder s = EntityWorkOrder.builder()
                .createdAt(new Date())
                .dateEnd(new Date(t + (10 * 60000)))
                .packages(entityPackage)
                .customer(customer)
                .build();

        element.create(s);
    }

    @Test
    public void update() {
        element.update(
                EntityWorkOrder.builder()
                        .id(2L)
                        .dateEnd(new Date(t + (10 * 60000)))
                        .packages(entityPackage)
                        .customer(customer)
                        .build()
        );
    }

    @Test
    public void delete() {
        element.delete(4L);
    }

    @Test
    public void getExistEntity() {
        Assert.assertNotNull(element.load(2L, 1));
    }

    @Test
    public void getCollectionEntity() {
        Assert.assertNotNull(element.load(1));
    }

}
