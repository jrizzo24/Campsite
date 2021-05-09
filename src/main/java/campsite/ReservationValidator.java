package campsite;

import com.sun.tools.jconsole.JConsoleContext;

import java.util.ArrayList;
import java.util.List;

public class ReservationValidator {

    private List<ReservationConstraint> constraints;
    private SiteReservation reservation;

    public ReservationValidator(SiteReservation reservation){
        this.reservation = reservation;
        this.constraints = new ArrayList<ReservationConstraint>();

        this.constraints.add(new ArrivalMinConstraint());
        this.constraints.add(new ArrivalMaxConstraint());
        this.constraints.add(new MaxDayConstraint());
    }

    public boolean validate(){
        boolean valid = true;
        for (ReservationConstraint constraint : constraints){
           valid = constraint.constraintPassed(reservation);
           if (!valid){
               reservation.setInValidReason(constraint.getReason());
               break;
           }
        }
        return valid;
    }
}
