package com.acme.dummyservice.utils;

@FunctionalInterface
public interface CheckedFunction<T,R> {
    R apply(T t) throws Exception;
}