package in.agricred.authservice;
import jakarta.persistence.*;
@Entity @Table(name="user_accounts", uniqueConstraints=@UniqueConstraint(columnNames="email")) public class UserAccount { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id; @Column(nullable=false) public String name; @Column(nullable=false) public String email; @Column(nullable=false) public String passwordHash; @Column(nullable=false) public String role; public UserAccount(){} }
