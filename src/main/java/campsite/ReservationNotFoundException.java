package campsite;

import java.util.function.Supplier;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(Integer id) {
        super("Could not find reservation ID: " + id);
    }
}
