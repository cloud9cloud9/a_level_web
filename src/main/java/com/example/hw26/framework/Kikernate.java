package com.example.hw26.framework;

import com.example.hw26.annotation.Column;
import com.example.hw26.annotation.Table;
import com.example.hw26.entity.User;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Kikernate {
    private static String insert = "INSERT INTO %s.%s (%s) VALUES (%s);";
    private static String getById = "SELECT (%s) FROM %s.%s.u WHERE u.id = id";
    private static String getAll = "SELECT (%s) FROM %s.%s.u";
    private static String update = "UPDATE %s.u SET (%s) = %s WHERE u.id = id;";
    private static String delete = "DELETE FROM %s.u WHERE u.id = id;";


    public static void generateInsert(User user) {
        String fieldName = readFieldName(getDeclaredFields());
        String fieldValue = readFiledValue(user, getDeclaredFields());
        System.out.println(String.format(insert, getTableInfo().schema(), getTableInfo().name(), fieldName, fieldValue));
    }

    public static void generateSelectById() {
        String fieldName = readFieldName(getDeclaredFields());
        System.out.println(String.format(getById, fieldName, getTableInfo().schema(), getTableInfo().name()));
    }

    public static void genereteSelectAll() {
        System.out.println(String.format(getAll, readFieldName(getDeclaredFields()),
                getTableInfo().schema(), getTableInfo().name()));
    }

    public static void generateUpdate(User user) {
        String fieldName = readFieldName(getDeclaredFields());
        String fieldValue = readFiledValue(user, getDeclaredFields());
        System.out.println(String.format(update, getTableInfo().name(), fieldName, fieldValue));
    }

    public static void generateDelete() {
        System.out.println(String.format(delete, getTableInfo().name()));
    }

    private static String convertFieldToString(Field field, User user) {
        try {
            return String.valueOf(field.get(user));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String readFieldName(Field[] fields) {
        return Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(field -> field.getAnnotation(Column.class))
                .map(Column::name)
                .collect(Collectors.joining(", "));
    }

    private static String readFiledValue(User user, Field[] fields) {
        return Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(Column.class))
                .peek(field -> field.setAccessible(true))
                .map(field -> convertFieldToString(field, user))
                .map(field -> "'" + field + "'")
                .collect(Collectors.joining(", "));
    }

    private static Table getTableInfo() {
        return User.class.getAnnotation(Table.class);
    }

    private static Field[] getDeclaredFields() {
        return User.class.getDeclaredFields();
    }
}
