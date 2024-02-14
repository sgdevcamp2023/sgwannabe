package com.lalala.user.domain;

import java.time.LocalDateTime;

import lombok.*;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.MODULE)
@DynamicInsert
@Table(
        name = "\"user\"",
        uniqueConstraints = {@UniqueConstraint(name = "EMAIL_UNIQUE", columnNames = "email")})
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, name = "email", columnDefinition = "varchar(60)")
    private String email;

    @Column(nullable = false, columnDefinition = "char(68)")
    private String password;

    @Column(nullable = false, columnDefinition = "varchar(15)")
    private String nickname; // 사용자 이름, 닉네임 사용 가능

    @Column(nullable = false, columnDefinition = "varchar(15) default 'USER'")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false, columnDefinition = "varchar(10) default 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ColumnDefault("''")
    private String profile;

    private LocalDateTime lastAccess;

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void changeStatus(Status newStatus) {
        this.status = newStatus;
    }

    public void changeUserRole(Role role) {
        this.role = role;
    }

    public void changeProfile(String profile) {
        this.profile = profile;
    }

    public User(String nickname, String email, String password, Status status) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.status = status;
    }
}
