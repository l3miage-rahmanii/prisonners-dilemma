package fr.uga.l3miage.pc.Exceptions.Technical;

public class BadRequestException extends Exception {
    public BadRequestException(String message) {
        super(message);
    }
}
