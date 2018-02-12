package com.epam.rd.util;

import com.epam.rd.model.entity.EntityPackage;

import java.util.Comparator;

public class PackageSortByName implements Comparator<EntityPackage> {

    @Override
    public int compare(EntityPackage o1, EntityPackage o2) {
        String str1 = o1.getName();
        String str2 = o2.getName();

        return str1.compareTo(str2);
    }
}