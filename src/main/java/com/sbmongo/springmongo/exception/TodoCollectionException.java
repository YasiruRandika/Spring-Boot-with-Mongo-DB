package com.sbmongo.springmongo.exception;

public class TodoCollectionException extends Exception{
    private static final long serialVersionUID = 1;

    public TodoCollectionException(String message) {
        super(message);
    }

    public static String notFoundExceString(String id) {
        return "Todo with " + id + "not found!";
    }

    public static String TodoAlreadyExists() {
        return "Todo with given name already exists";
    }
    
}
