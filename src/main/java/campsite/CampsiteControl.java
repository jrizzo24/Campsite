package campsite;

import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RestController
public class CampsiteControl {

    private final SiteReservationRepo repo;

    private static final int MAX_AVAILABILITY = Campsite.MONTH + Campsite.DAY_MAX;

    CampsiteControl(SiteReservationRepo repo){
        this.repo = repo;
    }

    @GetMapping("/")
    String sayHello(){
        return "Welcome to Camp Lava Rock!";
    }

    @GetMapping("/availability")
    List<Availability> getAvailability(@RequestParam(required = false) String start,
                                       @RequestParam(required = false) String end)  {
        LocalDate startDate = null;
        LocalDate endDate   = null;
        try {
            // startDate Defaults to today, endDate Defaults to a month after
            startDate = (start != null ? LocalDate.parse(start) : LocalDate.now());
            endDate = (end != null ? LocalDate.parse(end) : startDate.plusDays(Campsite.MONTH));
            // Just show the one day if endDate isn't after startDate
            if (endDate.isBefore(startDate))
                endDate = startDate;
            // Enforce a max date range for performance sake
            // Users don't need to see more than a month + 3 days in the future. That is all they can book
            if (ChronoUnit.DAYS.between(startDate, endDate) > MAX_AVAILABILITY)
                endDate = startDate.plusDays(MAX_AVAILABILITY);
        }
        catch (DateTimeParseException e){
            throw new DateFormatException();
        }

        return Availability.generate(repo, startDate, endDate);
    }

    @PostMapping("/reservation")
    SiteReservation newReservation(@RequestBody SiteReservation reservation){
        reservation.populateDays();
        if (reservation.isValid()) {
            reservation = save(reservation);
        }
        else {
            throw new InvalidReservationException(reservation);
        }
        return reservation;
    }

    @GetMapping("/reservation/{id}")
    SiteReservation getReservation(@PathVariable Integer id) {
        return repo.findById(id).orElseThrow(() -> new ReservationNotFoundException(id));
    }

    @PutMapping("/reservation/{id}")
    SiteReservation putReservation(@RequestBody SiteReservation newReservation, @PathVariable Integer id){
        SiteReservation reservation = new SiteReservation();

        try {
            reservation = repo.findById(id).get();
        } catch (NoSuchElementException e){
            throw new ReservationNotFoundException(id);
        }

        reservation.update(newReservation);
        if (reservation.isValid()) {
            reservation = save(reservation);
        }
        else {
            throw new InvalidReservationException(reservation);
        }
        return reservation;
    }

    @DeleteMapping("reservation/{id}")
    void deleteReservation(@PathVariable Integer id){
       repo.deleteById(id);
    }

    /*
     * Helper function to save a reservation
     */
    private SiteReservation save(SiteReservation reservation){
        try {
            reservation = repo.save(reservation);
        }
        catch (RuntimeException e){
            throw new DayReservedException();
        }
        return reservation;
    }

}
