package com.epam.rd;


import com.epam.rd.model.ModelPackage;
import com.epam.rd.util.PackageSortByPrice;

import java.text.ParseException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws ParseException {
        ModelPackage factory = new ModelPackage(1);
        List packages = factory.load();
        //    EntityPackage pack = factory.load((long)13);
        //     request.setAttribute("pack", pack);

            PackageSortByPrice epcomp = new PackageSortByPrice();
            packages.sort(epcomp);

        for (int i = 0; i < packages.size(); i++) {
            System.out.println(packages.get(i));
        }

    }
}