package fr.uga.l3miage.pc.Exceptions.Rest;

public class BadRequestRestException extends RuntimeException {
    public BadRequestRestException(String message) {
        super(message);
    }
}