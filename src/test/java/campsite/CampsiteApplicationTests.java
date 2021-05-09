package campsite;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class CampsiteApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private SiteReservationRepo repo;

	private static final LocalDate today = LocalDate.now();

	@Test
	void populateDaysTest() {
	    // Test both checkIn and checkOut populated
		SiteReservation reservation = new SiteReservation();
		LocalDate checkIn = LocalDate.of(2022, 1, 1);
		LocalDate checkOut = LocalDate.of(2022, 1, 3);
		reservation.setCheckIn(checkIn);
		reservation.setCheckOut(checkOut);

		List<LocalDate> days = reservation.getDays();
		assert(days.get(0).isEqual(checkIn));
		assert(days.get(1).isEqual(checkIn.plusDays(1)));
		assert(days.get(2).isEqual(checkOut));

		//Just checkIn populated
		reservation = new SiteReservation();
		reservation.setCheckIn(checkIn);
		days = reservation.getDays();
		assert(days.get(0).isEqual(checkIn));

		//Just checkOut populated
		reservation = new SiteReservation();
		reservation.setCheckOut(checkOut);
		days = reservation.getDays();
		assert(days.get(0).isEqual(checkOut));
	}

	@Test
	void validationTest() {
	    SiteReservation reservation = new SiteReservation();

	    // valid 3 day reservation (inclusive)
		reservation.setCheckIn(today.plusDays(2));
		reservation.setCheckOut(reservation.getCheckIn().plusDays(2));
		assert(reservation.isValid());

		// invalid - too soon
		reservation = new SiteReservation();
		reservation.setCheckIn(today.plusDays(1));
		reservation.setCheckOut(reservation.getCheckIn().plusDays(2));
		assert(!reservation.isValid());

		// invalid - too late
		reservation = new SiteReservation();
		reservation.setCheckIn(today.plusDays(32));
		reservation.setCheckOut(reservation.getCheckIn().plusDays(2));
		assert(!reservation.isValid());

		// invalid - too many days
		reservation = new SiteReservation();
		reservation.setCheckIn(today.plusDays(10));
		reservation.setCheckOut(reservation.getCheckIn().plusDays(3));
		assert(!reservation.isValid());
	}

	@Test
	void helloTest() throws Exception {
		MockMvcRequestBuilders reqBuilder;
	    mvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Welcome")));
	}

}
