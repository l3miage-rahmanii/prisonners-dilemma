package fr.uga.l3miage.pc.Exceptions.Technical;

public class NotFoundClientEntityException extends RuntimeException {
  public NotFoundClientEntityException(String message) {
    super(message);
  }
}
