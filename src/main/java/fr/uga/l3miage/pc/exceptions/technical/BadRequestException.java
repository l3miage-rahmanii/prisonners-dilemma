package fr.uga.l3miage.pc.exceptions.technical;

public class BadRequestException extends Exception {
    public BadRequestException(String message) {
        super(message);
    }
}
