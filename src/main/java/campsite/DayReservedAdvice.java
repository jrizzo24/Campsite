package campsite;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DayReservedAdvice {

    @ResponseBody
    @ExceptionHandler(DayReservedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String reservationNotFoundHandler(DayReservedException ex) {
        return  ex.getMessage();
    }
}

