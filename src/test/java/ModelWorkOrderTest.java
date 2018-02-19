
import com.epam.rd.DataBaseUtility;
import com.epam.rd.model.ModelCustomer;
import com.epam.rd.model.ModelPackage;
import com.epam.rd.model.ModelWorkOrder;
import com.epam.rd.model.entity.EntityCustomer;
import com.epam.rd.model.entity.EntityPackage;
import com.epam.rd.model.entity.EntityWorkOrder;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ModelWorkOrderTest {
    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(30);

    private ModelWorkOrder element = new ModelWorkOrder();
    private BasicDataSource dataSource = DataBaseUtility.getDataSource();
    private ModelCustomer sutCustomer = new ModelCustomer(dataSource.getConnection());

    EntityCustomer customer = EntityCustomer.builder().id(101L).build();
    EntityPackage entityPackage = EntityPackage.builder().id(22L).build();
    Calendar date = Calendar.getInstance();
    long t = date.getTimeInMillis();

    public ModelWorkOrderTest() throws SQLException {
    }


    @Test
    public void createRecord() {
        EntityWorkOrder s = EntityWorkOrder.builder()
                .createdAt(new Date())
                .dateEnd(new Date(t + (10 * 600000)))
                .packages(entityPackage)
                .customer(customer)
                .status(true)
                .build();

        element.create(s);

        EntityWorkOrder wo = element.loadByDetails(customer, entityPackage);
        Assert.assertNotNull(wo);

        element.deleteCompletely(wo);
    }

    @Test
    public void update() {
        EntityWorkOrder wo = element.loadByDetails(customer, entityPackage);

        element.update(
                EntityWorkOrder.builder()
                        .id(wo.getId())
                        .createdAt(wo.getCreatedAt())
                        .dateEnd(new Date(wo.getDateEnd().getTime() + 7000000))
                        .packages(wo.getPackages())
                        .customer(wo.getCustomer())
                        .status(true)
                        .build()
        );

        EntityWorkOrder orderUpdated = element.loadByDetails(customer, entityPackage);


        Assert.assertTrue(wo.getDateEnd().getTime() < orderUpdated.getDateEnd().getTime());
        Assert.assertEquals("true", orderUpdated.getStatus().toString());


    }

    @Test
    public void forceChargeFail() {

        EntityCustomer s = EntityCustomer.builder()
                .firstname("Name")
                .lastname("LastName")
                .mail("mail")
                .balance(80)
                .password("pass").build();

        sutCustomer.create(s);

        EntityWorkOrder wo = EntityWorkOrder.builder()
                .createdAt(new Date())
                .dateEnd(new Date(t + (10 * 600000)))
                .packages(entityPackage)
                .customer(s)
                .status(true)
                .build();

        element.create(wo);
        EntityWorkOrder orderTocharge = element.loadByDetails(s, entityPackage);

        element.forceCharge(orderTocharge.getId());

        EntityWorkOrder orderAfterCharge = element.loadByDetails(s, entityPackage);

        Assert.assertFalse(orderAfterCharge.getStatus());

        sutCustomer.delete(s);
        element.deleteCompletely(orderAfterCharge);

    }

    @Test
    public void forceChargeSuccess() {

        EntityCustomer s = EntityCustomer.builder()
                .firstname("Name")
                .lastname("LastName")
                .mail("mail")
                .balance(800)
                .password("pass").build();

        sutCustomer.create(s);

        EntityWorkOrder wo = EntityWorkOrder.builder()
                .createdAt(new Date())
                .dateEnd(new Date(t + (10 * 600000)))
                .packages(entityPackage)
                .customer(s)
                .status(true)
                .build();

        element.create(wo);
        EntityWorkOrder orderTocharge = element.loadByDetails(s, entityPackage);

        element.forceCharge(orderTocharge.getId());

        EntityWorkOrder orderAfterCharge = element.loadByDetails(s, entityPackage);

        Assert.assertTrue(orderAfterCharge.getStatus());

        sutCustomer.delete(s);
        element.deleteCompletely(orderAfterCharge);

    }

    @Test
    public void scheduledCharging() throws InterruptedException {

        ModelPackage factoryPackage = new ModelPackage(1);

        EntityCustomer cust = EntityCustomer.builder()
                .firstname("Steve")
                .lastname("Jobs")
                .mail("apple_steve@gmail.com")
                .balance(300)
                .password("pass").build();

        sutCustomer.create(cust);
        EntityCustomer customer = sutCustomer.loadByEmail("apple_steve@gmail.com");


        EntityPackage pack = factoryPackage.loadById(19L);

        Date dateToCharge = new Date();
        dateToCharge.setTime(dateToCharge.getTime() + 60 * 50);

        EntityWorkOrder order = new EntityWorkOrder(customer, pack, new Date(), dateToCharge, true);
        element.create(order);
        System.out.println(element.load(1).get(element.load(1).size() - 1).getDateEnd());

        EntityWorkOrder wo = element.loadByDetails(customer, pack);

        executorService.scheduleAtFixedRate(new Thread(() -> element.forceChargeByOrderDetails(wo)),
                order.getDateEnd().getTime() - new Date().getTime(), 3000L, TimeUnit.MILLISECONDS);

        Thread.sleep(30000);
        EntityWorkOrder orderAfterCharge = element.loadByDetails(customer, pack);
        Assert.assertFalse(orderAfterCharge.getStatus());

        sutCustomer.delete(customer);
        element.deleteCompletely(orderAfterCharge);
    }

    @Test
    public void getExistEntity() {
        EntityWorkOrder s = EntityWorkOrder.builder()
                .createdAt(new Date())
                .dateEnd(new Date(t + (10 * 600000)))
                .packages(entityPackage)
                .customer(customer)
                .status(true)
                .build();

        element.create(s);

        EntityWorkOrder wo = element.loadByDetails(customer, entityPackage);
        Assert.assertNotNull(wo);
        Assert.assertNotNull(element.load(60L, 1));

        element.deleteCompletely(wo);
    }

    @Test
    public void getCollectionEntity() {
        Assert.assertNotNull(element.load(1));
    }

}
