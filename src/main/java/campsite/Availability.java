package campsite;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Availability {

    private LocalDate day;
    private boolean available;

    public Availability(LocalDate day, boolean available){
        this.day       = day;
        this.available = available;
    }

    /*
     * Get the reserved days from the database into a Set.
     * Loop Through the days from start - end, and if a day
     * is in the set of reserved days, * mark it unavailable
     */
    public static List<Availability> generate(SiteReservationRepo repo, LocalDate start, LocalDate end){
        List<Availability> availability = new ArrayList<Availability>();
        Set<Date> unavailable           = repo.findUnavailable(start, end);

        for (LocalDate day = start; !day.isAfter(end); day = day.plusDays(1)){
            boolean isAvailable = !unavailable.contains(Date.valueOf(day));
            availability.add(new Availability(day, isAvailable));
        }

        return availability;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
