import com.epam.rd.DataBaseUtility;
import com.epam.rd.model.ModelCustomer;
import com.epam.rd.model.entity.EntityCustomer;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;


public class ModelCustomerTest {
    BasicDataSource dataSource = DataBaseUtility.getDataSource();
    ModelCustomer element = new ModelCustomer(dataSource.getConnection());

    public ModelCustomerTest() throws SQLException {
    }


    @Test
    public void createRecord() throws Exception {
        EntityCustomer s = EntityCustomer.builder()
                .firstname("Name")
                .lastname("LastName")
                .mail("mail")
                .balance(80)
                .password("pass").build();
        s.setPackages(Arrays.asList(14L));

		element.create(s);
    }

    @Test
    public void update() {
        element.update(
                EntityCustomer.builder()
                        .id(49L)
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
                        .id(49L)
                        .build()
        );
    }

    @Test
    public void getExistEntity() {
        Assert.assertNotNull(element.loadById(43L));
    }

    @Test
    public void getCollectionEntity() {
        Assert.assertNotNull(element.load());
    }

}
