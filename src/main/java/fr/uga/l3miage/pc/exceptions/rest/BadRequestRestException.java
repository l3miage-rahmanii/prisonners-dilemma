package fr.uga.l3miage.pc.exceptions.rest;

public class BadRequestRestException extends RuntimeException {
    public BadRequestRestException(String message) {
        super(message);
    }
}