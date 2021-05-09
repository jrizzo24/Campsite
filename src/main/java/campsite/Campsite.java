package campsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Campsite {

	public static final int MONTH   = 31;
	public static final int DAY_MAX = 3;

	public static void main(String[] args) {
		SpringApplication.run(Campsite.class, args);
	}

}
