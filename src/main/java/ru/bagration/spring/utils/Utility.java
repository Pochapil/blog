package ru.bagration.spring.utils;

import java.util.UUID;

public class Utility {

    public static String generateUUID(){
        return UUID.randomUUID().toString();
    }

}
