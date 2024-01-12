package userserver.domain;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.Map;


@Builder
@Entity
@Getter
@NoArgsConstructor // TODO (access = AccessLevel.PROTECTED)
@AllArgsConstructor // TODO (access = AccessLevel.PRIVATE)
@Table(name="\"user\"")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "number") // TODO columnDefinition = "int(11)"
    private Long number;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false) //TODO how long 인지 기재
    private String password;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private Status status;

    @Type(JsonType.class)
    @Column(name = "PROFILE", columnDefinition = "json")
    private Map<String, Object> profile;


    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void changeStatus(Status newStatus) {
        this.status = newStatus;
    }


}
