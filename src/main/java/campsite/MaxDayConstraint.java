package campsite;

public class MaxDayConstraint implements ReservationConstraint{

    private static final String REASON = "Maximum days allowed to be reserved exceeded";

    @Override
    public boolean constraintPassed(SiteReservation reservation) {
        return ( reservation.getDays().size() <= Campsite.DAY_MAX );
    }

    @Override
    public String getReason() {
        return REASON;
    }
}
