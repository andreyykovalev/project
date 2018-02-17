package com.epam.rd;


import com.epam.rd.model.ModelCustomer;
import com.epam.rd.model.ModelPackage;
import com.epam.rd.model.ModelWorkOrder;
import com.epam.rd.model.entity.EntityCustomer;
import com.epam.rd.model.entity.EntityPackage;
import com.epam.rd.model.entity.EntityWorkOrder;
import org.apache.commons.dbcp2.BasicDataSource;


import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;


public class Main {
    public static void main(String[] args) throws ParseException, SQLException {
        BasicDataSource dataSource = DataBaseUtility.getDataSource();
         ModelCustomer factory = new ModelCustomer(dataSource.getConnection());

        EntityCustomer customer = EntityCustomer.builder()
                .firstname("Name")
                .lastname("LastName")
                .mail("mail")
                .balance(80)
                .password("pass").build();
        customer.setPackages(Arrays.asList(16L, 14L));
factory.create(customer);
    }
}