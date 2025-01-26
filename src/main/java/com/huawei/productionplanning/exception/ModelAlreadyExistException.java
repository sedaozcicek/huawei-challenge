package com.huawei.productionplanning.exception;

public class ModelAlreadyExistException extends RuntimeException{
    public ModelAlreadyExistException(String message){
        super(message);
    }
}
