package com.litcode;

public class Converter {
    public static Object convert(String value, String type) {
        if (type.equals("int")) {
            return Integer.valueOf(value);
        } else if (type.equals("double")) {
            return Double.valueOf(value);
        } else if (type.equals("boolean")) {
            return Boolean.valueOf(value);
        } else if (type.equals("String")) {
            return value;
        } else {
            throw new IllegalArgumentException("Unsupported type: " + type);
        }
    }
}