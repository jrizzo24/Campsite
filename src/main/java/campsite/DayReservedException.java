package campsite;

public class DayReservedException extends RuntimeException {
    public DayReservedException(){
        super("One of the requested Days has already been reserved");
    }
}
