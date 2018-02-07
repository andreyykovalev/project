package com.epam.rd.model;

import com.epam.rd.model.entity.EntityCustomer;
import com.epam.rd.model.entity.EntityWorkOrder;
import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ModelCustomer extends Model {
    Connection connection;
    private final String GET_USER_BY_ID = "SELECT * FROM CUSTOMER WHERE ID_CUSTOMER = %s";
    private final String GET_USER_BY_MAIL = "SELECT * FROM CUSTOMER WHERE MAIL = '%s'";
    private final String GET_USERS = "SELECT ID_CUSTOMER FROM CUSTOMER ";
    private final String GET_PACKAGES_BY_ID = "SELECT ID_PACKAGE FROM CUSTOMER_TO_PACKAGE WHERE ID_CUSTOMER = %s";
    private final String CREATE_CUSTOMER = "INSERT INTO CUSTOMER (FIRSTNAME, LASTNAME, MAIL, PASSWORD, BALANCE) " +
            "VALUES ('%s', '%s', '%s', '%s', '%f');";
    private final String CREATE_CUSTOMER_PACKAGES = "INSERT INTO `CUSTOMER_TO_PACKAGE` (`ID_CUSTOMER`, `ID_PACKAGE`) " +
            "VALUES (%d, %d)";
    private final String UPDATE = "UPDATE `CUSTOMER` %s WHERE `ID_CUSTOMER`='%d';";
    private final String DELETE = "DELETE FROM `CUSTOMER` WHERE `ID_CUSTOMER`='%d';";
    private final String DELETE_PACKAGES = "DELETE FROM `CUSTOMER_TO_PACKAGE` WHERE `ID_CUSTOMER`='%d';";

    public ModelCustomer(Connection connection) {
        this.connection = connection;
    }

    public ModelCustomer() {
    }

    public EntityCustomer loadById(Long id) {
        List<Map> collection = query(String.format(GET_USER_BY_ID, id));

        final EntityCustomer[] customer = new EntityCustomer[1];
        if (collection != null) {
            ArrayList<Long> attachedId = new ArrayList<>();
            collection.forEach((Map e) -> customer[0] = EntityCustomer.builder()
                    .id(Long.valueOf((Integer) e.get("id_customer")))
                    .firstname((String) e.get("firstname"))
                    .lastname((String) e.get("lastname"))
                    .mail((String) e.get("mail"))
                    .balance((Double) e.get("balance"))
                    .password((String) e.get("password"))
                    .packages(attachedId)
                    .build());

            query(String.format(GET_PACKAGES_BY_ID, id))
                    .forEach((Map e) -> attachedId.add(Long.valueOf((Integer) e.get("id_package"))));
        }
        return customer[0];
    }

    public List<EntityCustomer> load(String whereCause) {
        List<Map> collection = query(GET_USERS + whereCause);
        List<EntityCustomer> list = new ArrayList<>();
        collection.forEach((Map e) -> list.add(loadById(Long.valueOf((Integer) e.get("id_customer")))));
        return list;
    }

    public EntityCustomer loadByEmail(String email) {
        List<Map> collection = query(String.format(GET_USER_BY_MAIL, email));

        final EntityCustomer[] customer = new EntityCustomer[1];
        if (collection != null) {
            ArrayList<Long> attachedId = new ArrayList<>();
            collection.forEach((Map e) -> customer[0] = EntityCustomer.builder()
                    .id(Long.valueOf((Integer) e.get("id_customer")))
                    .firstname((String) e.get("firstname"))
                    .lastname((String) e.get("lastname"))
                    .mail((String) e.get("mail"))
                    .balance((Double) e.get("balance"))
                    .password((String) e.get("password"))
                    .packages(attachedId)
                    .build());

        }
        return customer[0];
    }

    public List<EntityCustomer> load() {
        return load("");
    }

    public void update(EntityCustomer customer) {
        if (customer.getId() != null) {
            final EntityCustomer customerAndPackage = isAvailablePackageExist(customer);
            String genFieldEntity = "SET" + String.join(",",
                    packSet("firstname", customer.getFirstname()),
                    packSet("lastname", customer.getLastname()),
                    packSet("mail", customer.getMail()),
                    packSet("balance", customer.getBalance()),
                    packSet("password", customer.getPassword()));

            update(String.format(UPDATE, genFieldEntity, customer.getId()));
            deletePackages(customer);
            customer.getPackages().forEach((Long v) -> update(String.format(CREATE_CUSTOMER_PACKAGES, customerAndPackage.getId(), v)));
        } else {
            throw new UnsupportedOperationException("EntityCustomer id is null");
        }
    }


    private EntityCustomer isAvailablePackageExist(EntityCustomer customer) {
        ModelWorkOrder wo = new ModelWorkOrder();
        List<EntityWorkOrder> list = wo.load(1);
        list.forEach((EntityWorkOrder o) -> {
            if (o.getCustomer().getId().longValue() == customer.getId().longValue() && !o.getStatus()) {
                if (o.getPackages().getPrice() < customer.getBalance()) {
                    customer.setBalance(customer.getBalance() - o.getPackages().getPrice());
                    o.setDateEnd(Date.from(LocalDate.now()
                            .plusMonths(1)
                            .atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    o.setStatus(true);
                    wo.update(o);
                }
            }
        });

        return customer;
    }

    public void delete(EntityCustomer customer) {
        update(String.format(DELETE, customer.getId()));
        deletePackages(customer);
    }

    public boolean create(EntityCustomer user) throws Exception {
        String query = "INSERT INTO customer (firstname, lastname, mail, password, balance) VALUES(?,?,?,?,?)";
        boolean rowInserted = false;

        PreparedStatement statement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, user.getFirstname());
        statement.setString(2, user.getLastname());
        statement.setString(3, user.getMail());
        statement.setString(4, user.getPassword());
        statement.setDouble(5, user.getBalance());
        statement.executeUpdate();

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong(1));
            }
        }
        if (!user.getPackages().isEmpty()) {
            user.getPackages().forEach((Long v) -> update(String.format(CREATE_CUSTOMER_PACKAGES, lastId, v)));
        }
        return rowInserted;
    }
    private void deletePackages(EntityCustomer customer) {
        update(String.format(DELETE_PACKAGES, customer.getId()));
    }

}
