package spring.auth.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "\"user\"",
        uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@NoArgsConstructor
@AllArgsConstructor
@Getter
@DynamicInsert //column default value 넣기
@Builder
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private ERole role;

    @ColumnDefault("'ACTIVE'")
    private String status;

    private String profileImg;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void changeUserRole(ERole role) {
        this.role = role;
    }

    public void changeProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public void changePassword(String password) {
        this.password = password;
    }


}
