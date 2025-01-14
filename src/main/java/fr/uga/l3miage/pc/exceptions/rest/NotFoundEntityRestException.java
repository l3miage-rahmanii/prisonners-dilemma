package fr.uga.l3miage.pc.exceptions.rest;

public class NotFoundEntityRestException extends RuntimeException {
    public NotFoundEntityRestException(String message) {
        super(message);
    }
}