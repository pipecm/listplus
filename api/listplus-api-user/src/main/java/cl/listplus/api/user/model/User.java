package cl.listplus.api.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Entity
@Table(name = "lp_user")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "user_id")
    private UUID id;

    @Column(name = "user_name")
    private String username;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_password")
    private String password;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "user_role")
    private Role role;

    @Column(name = "user_created_on")
    private LocalDateTime createdOn;

    @Column(name = "user_last_updated_on")
    private LocalDateTime lastUpdatedOn;

    @Column(name = "user_active")
    private boolean active;
}
