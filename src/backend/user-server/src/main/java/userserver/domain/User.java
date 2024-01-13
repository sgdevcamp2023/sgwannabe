package userserver.domain;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.MODULE)
@Table(name="\"user\"", uniqueConstraints={@UniqueConstraint(name="EMAIL_UNIQUE", columnNames = "email")})
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, name="email", columnDefinition = "varchar(60)")
    private String email;

    @Column(nullable = false, columnDefinition = "char(68)")
    private String password;

    @Column(nullable = false, columnDefinition = "varchar(15)")
    private String nickname; // 사용자 이름, 닉네임 사용 가능

    @Column(nullable = false, columnDefinition = "varchar(15) default 'ROLE_USER'")
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(nullable = false, columnDefinition = "varchar(10) default 'ENABLE'")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Type(JsonType.class)
    @Column(name = "PROFILE", columnDefinition = "json")
//    @ColumnDefault("https://") // TODO default 이미 지정
    private List<Map<String, Object>> profile;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastAccess;

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void changeStatus(Status newStatus) {
        this.status = newStatus;
    }


}
