package in.agricred.farmerservice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface RepaymentRepository extends JpaRepository<RepaymentRecord, Long> {
    List<RepaymentRecord> findByEmailIgnoreCaseOrderByDueDateDesc(String email);
}
