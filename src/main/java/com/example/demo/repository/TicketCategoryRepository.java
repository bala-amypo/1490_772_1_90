import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.TicketCategory;

public interface TicketCategoryRepository extends JpaRepository<TicketCategory, Long> {
    boolean existsByCategoryName(String categoryName);
}
