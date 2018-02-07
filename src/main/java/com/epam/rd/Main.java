package com.epam.rd;


import com.epam.rd.model.ModelWorkOrder;
import com.epam.rd.model.entity.EntityWorkOrder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParseException {
        ModelWorkOrder modelWorkOrder = new ModelWorkOrder();
        List<EntityWorkOrder> listProducts = modelWorkOrder.load(1);
        List dateEnd = new ArrayList();
        for (int i = 0; i < listProducts.size(); i++) {
            Date date = listProducts.get(i).getDateEnd();
            String str = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(date);
            dateEnd.add(str);
        }
        for (int i = 0; i < listProducts.size(); i++) {
            System.out.println(listProducts.get(i).getCustomer().getFirstname());
            System.out.println(dateEnd.get(i));
        }




    }
}