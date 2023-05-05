package com.litcode;

public class Converter {
    public static <T> T convert(String value, String type) {
        if (type.equals("int")) {
            return (T) Integer.valueOf(value);
        } else if (type.equals("double")) {
            return (T) Double.valueOf(value);
        } else if (type.equals("boolean")) {
            return (T) Boolean.valueOf(value);
        } else if (type.equals("String")) {
            return (T) value;
        } else {
            throw new IllegalArgumentException("Unsupported type: " + type);
        }
    }
}