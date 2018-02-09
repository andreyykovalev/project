package com.epam.rd;


import com.epam.rd.model.ModelPackage;
import com.epam.rd.util.PackageSortByPrice;

import java.text.ParseException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws ParseException {
        ModelPackage factory = new ModelPackage(1);
        List packages;
        int count = 0;
        for (int i = 0; i < 10000; i++) {
            packages = factory.load();
            count++;
            System.out.println(count);
        }
    }
}