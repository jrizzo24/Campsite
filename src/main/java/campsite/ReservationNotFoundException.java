package campsite;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(Integer id) {
        super("Could not find reservation ID: " + id);
    }
}
