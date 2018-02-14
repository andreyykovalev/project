package com.epam.rd;


import com.epam.rd.model.ModelCustomer;
import com.epam.rd.model.ModelPackage;
import com.epam.rd.model.ModelWorkOrder;
import com.epam.rd.model.entity.EntityCustomer;
import com.epam.rd.model.entity.EntityPackage;
import com.epam.rd.model.entity.EntityWorkOrder;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Main {
    public static void main(String[] args) throws ParseException {


        Date date = new Date();
        ModelCustomer modelCustomer = new ModelCustomer();
        ModelWorkOrder modelWorkOrder = new ModelWorkOrder();
        ModelPackage modelPackage = new ModelPackage(1);
        EntityWorkOrder workOrder = modelWorkOrder.load(35L, 1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        EntityCustomer cust = modelCustomer.loadById(43L);
        EntityPackage pack = modelPackage.loadById(13L);


        System.out.println(workOrder.getStatus());
        workOrder.setDateEnd(date);
        Date updated = workOrder.getDateEnd();
        System.out.println(updated);

        EntityWorkOrder newBook2 = EntityWorkOrder.builder()
                .customer(cust)
                .packages(pack)
                .dateEnd(updated)
                .status(workOrder.getStatus())
                .createdAt(workOrder.getCreatedAt())
                .id(workOrder.getId())
                .build();

        modelWorkOrder.update(newBook2);

        System.out.println(modelWorkOrder.load(35L, 1).getDateEnd());
    }
}