package campsite;

public class InvalidReservationException extends RuntimeException {
    public InvalidReservationException(SiteReservation reservation) {
        super("Invalid Reservation Request: " + reservation.getInValidReason());
    }
}
