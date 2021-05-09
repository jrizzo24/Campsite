package campsite;

import org.apache.tomcat.jni.Local;

import java.time.LocalDate;

public class ArrivalMinConstraint implements ReservationConstraint{

    private static final String REASON = "Arrival time is less than one day away";

    @Override
    public boolean constraintPassed(SiteReservation reservation) {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDate arrival  = reservation.getCheckIn();
        // arrival date must be after tomorrow since checkIn is at midnight
        return arrival.isAfter(tomorrow);
    }

    @Override
    public String getReason() {
        return REASON;
    }
}
