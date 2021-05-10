package campsite;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import javax.persistence.*;

/*
 * Class to represent the reservation information
 */
@Entity
@Table(name = "reservations")
public class SiteReservation {

    @Id
    @GeneratedValue
    private Integer id;
    private String fullName;
    private String email;

    @Transient
    private LocalDate checkIn;
    @Transient
    private LocalDate checkOut;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="reserved_days", joinColumns=@JoinColumn(name="reservation_id"),
                     uniqueConstraints = @UniqueConstraint(columnNames = "day"))
    @Column(name="day")
    private List<LocalDate> days;

    @Transient
    private String inValidReason;

    public SiteReservation(){
        this.days = new ArrayList<LocalDate>();
    }

    public SiteReservation(String fullName, String email){
        this.fullName = fullName;
        this.email    = email;
        this.days     = new ArrayList<LocalDate>();
    }

    public void update(SiteReservation reservation){
        this.fullName = reservation.fullName;
        this.email    = reservation.email;
        this.checkIn  = reservation.checkIn;
        this.checkOut = reservation.checkOut;
        populateDays();
    }

    /*
     * Populate the days array based on the check-in and check-out dates
     */
    public void populateDays(){
       days.clear();
       if (checkIn != null && checkOut != null) {
           // Loop through days from checkIn to checkOut, inclusive
           for (LocalDate day = checkIn; !day.isAfter(checkOut); day = day.plusDays(1)) {
               days.add(day);
           }
       }
       else if (checkIn == null ^ checkOut == null) {
           LocalDate day = ( checkIn == null ? checkOut : checkIn );
           days.add(day);
       }
    }

    /*
     * Make sure the reservations does not violate any contraints
     */
    @JsonIgnore
    public boolean isValid(){
        ReservationValidator validator = new ReservationValidator(this);
        return validator.validate();
    }

    @Override
    public String toString() {
        return "SiteReservation{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", days=" + days +
                '}';
    }

    public void reserveDay(LocalDate day){
        days.add(day);
    }

    /*
     * Getters and Setters
     */
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getCheckIn() {
        return ( checkIn != null ? checkIn : days.get(0) );
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckout() {
        return ( checkOut != null ? checkOut : days.get(days.size()-1) );
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    @JsonIgnore
    public String getInValidReason() {
        return inValidReason;
    }

    public void setInValidReason(String inValidReason) {
        this.inValidReason = inValidReason;
    }

    public List<LocalDate> getDays() {
        //Populate the days array based on checkIn and checkOut if array is empty
        if (days.size() == 0){
            populateDays();
        }
        return days;
    }

    public void setDays(List<LocalDate> days) {
        this.days = days;
    }
}
