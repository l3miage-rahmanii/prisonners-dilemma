package fr.uga.l3miage.pc.exceptions.Rest;

public class NotFoundEntityRestException extends RuntimeException {
    public NotFoundEntityRestException(String message) {
        super(message);
    }
}