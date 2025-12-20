import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.demo.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByStatus(String status);
    List<Ticket> findByUser_Id(Long userId);
}
