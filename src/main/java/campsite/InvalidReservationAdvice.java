package campsite;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InvalidReservationAdvice {

    @ResponseBody
    @ExceptionHandler(InvalidReservationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String reservationNotFoundHandler(InvalidReservationException ex) {
        return  ex.getMessage();
    }
}
