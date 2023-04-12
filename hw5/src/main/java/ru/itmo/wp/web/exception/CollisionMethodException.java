package ru.itmo.wp.web.exception;

public class CollisionMethodException extends Exception {
    CollisionMethodException (String methodName) {
        super(methodName);
    }
}
