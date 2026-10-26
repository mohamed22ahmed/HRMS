package com.hrms.employee_service.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Arrays;

public final class GeneralUtils {
    private GeneralUtils() {}

    public static String[] nullProperties(Object source) {
        BeanWrapper wrapper = new BeanWrapperImpl(source);
        return Arrays.stream(wrapper.getPropertyDescriptors())
                .map(java.beans.PropertyDescriptor::getName)
                .filter(name -> wrapper.getPropertyValue(name) == null)
                .toArray(String[]::new);
    }
}
