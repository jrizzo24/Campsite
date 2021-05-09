package campsite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.sql.Date;
import java.util.List;
import java.util.Set;

@Repository
interface SiteReservationRepo extends JpaRepository<SiteReservation, Integer>{

    @Query(value  = "SELECT day FROM reserved_days WHERE day BETWEEN ?1 AND ?2", nativeQuery = true)
    Set<Date> findUnavailable(LocalDate start, LocalDate end);
}
