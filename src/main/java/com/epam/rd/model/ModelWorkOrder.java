package com.epam.rd.model;

import com.epam.rd.model.entity.EntityCustomer;
import com.epam.rd.model.entity.EntityPackage;
import com.epam.rd.model.entity.EntityWorkOrder;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.Date;


public class ModelWorkOrder extends Model {
    private final String CREATE = "INSERT INTO WORK_ORDER (ID_CUSTOMER, ID_PACKAGE, CREATED_AT, DATE_END, STATUS) " +
            "VALUES('%d','%d','%s','%s', '%d')";
    private final String UPDATE = "UPDATE WORK_ORDER SET DATE_END = '%s', STATUS = %d WHERE ID_WORK_ORDER = %d";
    private final String UPDATE_STATUS = "UPDATE WORK_ORDER SET STATUS = %d WHERE ID_WORK_ORDER = %d";
    private final String LOAD_BY_ID = "SELECT * FROM WORK_ORDER WHERE ID_WORK_ORDER = '%d'";
    private final String LOAD_BY_DETAILS = "SELECT * FROM WORK_ORDER WHERE ID_CUSTOMER = %d AND ID_PACKAGE = %d ";
    private final String LOAD_ALL = "SELECT * FROM WORK_ORDER";
    private final String DISABLE_PACKAGE = "DELETE FROM CUSTOMER_TO_PACKAGE WHERE ID_CUSTOMER = %d AND ID_PACKAGE = %d ";
    private final String DELETE_WORK_ORDER = "DELETE FROM WORK_ORDER WHERE ID_WORK_ORDER = %d ";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Connection connection;

    public ModelWorkOrder(Connection connection) {
        this.connection = connection;
    }

    public ModelWorkOrder() {

    }

    /**
     * @param item must include EntityPackage.id, EntityCustomer.id, createdAt, dateEnd
     */

    public void create(EntityWorkOrder item) {
        ModelCustomer modelCustomer = new ModelCustomer();
        ModelPackage modelPackage = new ModelPackage(1);
        EntityCustomer customer = modelCustomer.loadById(item.getCustomer().getId());
        EntityPackage packageInfo = modelPackage.loadById(item.getPackages().getId());
        if (customer != null && customer.getId() != null
                && packageInfo != null && packageInfo.getId() != null
                ) {
            item.setStatus((customer.getBalance() - packageInfo.getPrice()) > -1);
            int status;
            if (item.getStatus()) {
                status = 1;
            } else status = 0;
            update(String.format(CREATE, item.getCustomer().getId(),
                    item.getPackages().getId(),
                    sqlDate(item.getCreatedAt()),
                    sqlDate(item.getDateEnd()),
                    status)
            );
            customer.getPackages().add(item.getPackages().getId());
            customer.setBalance(customer.getBalance() - packageInfo.getPrice());
            modelCustomer.update(customer);
        } else {
            throw new UnsupportedOperationException("Incorrect data for order");
        }
    }

    /**
     * Can update only date end for.
     *
     * @param item must include EntityPackage.id, EntityCustomer.id, dateEnd, itemId.
     */
    public void update(EntityWorkOrder item) {

        int status;
        if (item.getStatus()) {
            status = 1;
        } else status = 0;
        update(String.format(UPDATE, sqlDate(item.getDateEnd()), status, item.getId()));
        return;

    }

    public void updateStatus(EntityWorkOrder item) {
        int status;
        if (item.getStatus()) {
            status = 1;
        } else status = 0;
        update(String.format(UPDATE_STATUS, status, item.getId()));
        return;

    }


    /**
     * For deattach bought package for incoming customer.
     *
     * @param id must include id
     */
    public void delete(Long id) {
        EntityWorkOrder item = load(id, 1);

        if (item.getCustomer().getBalance() - item.getPackages().getPrice() > -1) {
            ModelCustomer customerModel = new ModelCustomer();
            item.getCustomer().setBalance(item.getCustomer().getBalance() - item.getPackages().getPrice());
            item.setDateEnd(
                    Date.from(LocalDate.now()
                            .plusMonths(1)
                            .atStartOfDay(ZoneId.systemDefault()).toInstant())
            );
            update(item);
            customerModel.update(item.getCustomer());

        } else {
            update(String.format(DISABLE_PACKAGE, item.getCustomer().getId(), item.getPackages().getId()));
            item.setStatus(false);
            update(item);
        }

        logger.error("Incoming item is invalid");
    }


    public EntityWorkOrder loadByDetails(EntityCustomer customer,EntityPackage pack) {
        List<Map> collection = query(String.format(LOAD_BY_DETAILS, customer.getId(), pack.getId()));

        final EntityWorkOrder[] wItem = new EntityWorkOrder[1];
        if (collection != null) {
            collection.forEach((Map e) -> wItem[0] = EntityWorkOrder.builder()
                    .id(Long.parseLong((e.get("id_work_order")).toString()))
                    .createdAt(new Date(((Timestamp) e.get("created_at")).getTime()))
                    .dateEnd(new Date(((Timestamp) e.get("date_end")).getTime()))
                    .customer(customer)
                    .packages(pack)
                    .status((Boolean) e.get("status"))
                    .build());
        }
        return wItem[0];
    }

    public void deleteByDetails(EntityWorkOrder order) {
        EntityWorkOrder item = loadByDetails(order.getCustomer(), order.getPackages());

        if (item.getCustomer().getBalance() - item.getPackages().getPrice() > -1) {
            ModelCustomer customerModel = new ModelCustomer();
            item.getCustomer().setBalance(item.getCustomer().getBalance() - item.getPackages().getPrice());
            item.setDateEnd(
                    Date.from(LocalDate.now()
                            .plusMonths(1)
                            .atStartOfDay(ZoneId.systemDefault()).toInstant())
            );
            update(item);
            customerModel.update(item.getCustomer());

        } else {
            update(String.format(DISABLE_PACKAGE, item.getCustomer().getId(), item.getPackages().getId()));
            item.setStatus(false);
            update(item);
        }

        logger.error("Incoming item is invalid");
    }

    public EntityWorkOrder load(Long id, Integer idLanguage) {
        List<Map> collection = query(String.format(LOAD_BY_ID, id));
        ModelCustomer customer = new ModelCustomer();
        ModelPackage customerPackage = new ModelPackage(idLanguage);

        final EntityWorkOrder[] wItem = new EntityWorkOrder[1];
        if (collection != null) {
            collection.forEach((Map e) -> wItem[0] = EntityWorkOrder.builder()
                    .id(id)
                    .createdAt(new Date(((Timestamp) e.get("created_at")).getTime()))
                    .dateEnd(new Date(((Timestamp) e.get("date_end")).getTime()))
                    .customer(customer.loadById(Long.valueOf((Integer) e.get("id_customer"))))
                    .packages(customerPackage.loadById(Long.valueOf((Integer) e.get("id_package"))))
                    .status((Boolean) e.get("status"))
                    .build());
        }
        return wItem[0];
    }

    public List<EntityWorkOrder> load(Integer idLanguage) {
        List<Map> collection = query(LOAD_ALL);
        List<EntityWorkOrder> list = new ArrayList<>();
        collection.forEach((Map e) -> list.add(load(Long.valueOf((Integer) e.get("id_work_order")), idLanguage)));
        return list;

    }

    private String sqlDate(Date d) {
        return sdf.format(d);
    }
}
