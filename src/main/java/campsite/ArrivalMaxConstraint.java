package campsite;

import java.time.LocalDate;
import java.time.YearMonth;

public class ArrivalMaxConstraint implements ReservationConstraint{

    private static final String REASON = "Arrival date is more than one month away";

    @Override
    public boolean constraintPassed(SiteReservation reservation) {
        LocalDate today   = LocalDate.now();
        LocalDate arrival = reservation.getCheckIn();

        // Arrival must be in the next month
        YearMonth thisMonth = YearMonth.now();
        int daysInMonth     = thisMonth.lengthOfMonth();
        return arrival.isBefore(today.plusDays(daysInMonth));
    }

    @Override
    public String getReason() {
        return REASON;
    }
}
