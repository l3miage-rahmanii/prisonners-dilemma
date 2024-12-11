package fr.uga.l3miage.pc.Exceptions.Rest;

public class NotFoundEntityRestException extends RuntimeException {
    public NotFoundEntityRestException(String message) {
        super(message);
    }
}