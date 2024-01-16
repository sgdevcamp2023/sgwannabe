package authserver.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "\"user\"")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // h2로 테스트할 때만 사용
    private Long id;
    private String nickname;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime lastAccess;

    public User(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public void changeUserRole(Role role) {
        this.role = role;
    }

    public void changeLastAccess(LocalDateTime now) {
        this.lastAccess = now;
    }

    public void changeStatus(Status status) {
        this.status = status;
    }
}
