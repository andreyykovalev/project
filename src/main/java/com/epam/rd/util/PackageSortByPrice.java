package com.epam.rd.util;

import com.epam.rd.model.entity.EntityPackage;

import java.util.Comparator;

public class PackageSortByPrice implements Comparator<EntityPackage>{

    @Override
    public int compare(EntityPackage o1, EntityPackage o2) {
        return  (int) (o1.getPrice() - o2.getPrice());
    }
}
