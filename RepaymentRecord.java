package in.agricred.farmerservice;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "farmer_repayments")
public class RepaymentRecord {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(nullable = false, length = 254)
    public String email;
    @Column(nullable = false, length = 120)
    public String loanReference;
    @Column(nullable = false)
    public LocalDate dueDate;
    public LocalDate paymentDate;
    @Column(nullable = false, precision = 14, scale = 2)
    public BigDecimal amountDue;
    @Column(nullable = false, precision = 14, scale = 2)
    public BigDecimal amountPaid;
    @Column(nullable = false, length = 20)
    public String status = "PENDING";
    public Instant createdAt = Instant.now();
    public RepaymentRecord() {}
}
