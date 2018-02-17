import com.epam.rd.DataBaseUtility;
import com.epam.rd.model.ModelCustomer;
import com.epam.rd.model.entity.EntityCustomer;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ModelCustomerTest {
    private static final double DELTA = 1e-15;
    private BasicDataSource dataSource = DataBaseUtility.getDataSource();
    private ModelCustomer sut = new ModelCustomer(dataSource.getConnection());

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

        sut.create(s);
		Assert.assertEquals("Name" ,sut.loadByEmail("mail").getFirstname());
        Assert.assertEquals("LastName" ,sut.loadByEmail("mail").getLastname());
        Assert.assertEquals("mail" ,sut.loadByEmail("mail").getMail());
        Assert.assertEquals( 80.00,sut.loadByEmail("mail").getBalance(),DELTA);
        Assert.assertEquals( "pass",sut.loadByEmail("mail").getPassword());
        sut.delete(s);
    }


    @Test
    public void update() throws Exception {
        EntityCustomer s = EntityCustomer.builder()
                .firstname("Name")
                .lastname("LastName")
                .mail("mail")
                .balance(80)
                .password("pass").build();
        sut.create(s);

        sut.update(
                EntityCustomer.builder()
                        .id(s.getId())
                        .firstname("Jackie")
                        .lastname("Chan")
                        .mail("JackieChan@gmail.com")
                        .balance(100)
                        .password("password")
                        .packages(Arrays.asList(16L))
                        .build()
        );

        Assert.assertEquals("Jackie" ,sut.loadByEmail("JackieChan@gmail.com").getFirstname());
        Assert.assertEquals("Chan" ,sut.loadByEmail("JackieChan@gmail.com").getLastname());
        Assert.assertEquals("JackieChan@gmail.com" ,sut.loadById(s.getId()).getMail());
        Assert.assertEquals( 100.00,sut.loadByEmail("JackieChan@gmail.com").getBalance(),DELTA);
        Assert.assertEquals( "password",sut.loadByEmail("JackieChan@gmail.com").getPassword());

        sut.delete(s);
    }

    @Test
    public void delete() {
        EntityCustomer customer1 = EntityCustomer.builder()
                .firstname("Steve")
                .lastname("Jobs")
                .mail("apple_steve@gmail.com")
                .balance(80)
                .password("pass").build();

        sut.create(customer1);

        List<EntityCustomer> customers = sut.load();

        sut.delete(
                EntityCustomer.builder()
                        .id(customer1.getId())
                        .build()
        );

        List<EntityCustomer> customersAfterDelete = sut.load();
        Assert.assertEquals(customers.size(), customersAfterDelete.size() + 1);
    }

    @Test
    public void getExistEntity() {
        EntityCustomer s = EntityCustomer.builder()
                .firstname("Name")
                .lastname("LastName")
                .mail("mail")
                .balance(80)
                .password("pass").build();

        sut.create(s);
        Assert.assertNotNull(sut.loadById(s.getId()));

        sut.delete(s);
    }

    @Test
    public void getCollectionEntity() {
        List<EntityCustomer> customers = sut.load();
        for (int i = 0; i < customers.size(); i++) {
            Assert.assertNotNull(customers.get(i));
        }
    }


}
