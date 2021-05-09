package campsite;

public interface ReservationConstraint {
    public boolean constraintPassed(SiteReservation reservation);
    public String  getReason();
}
