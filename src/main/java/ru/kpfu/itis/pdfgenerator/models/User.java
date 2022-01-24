package ru.kpfu.itis.pdfgenerator.models;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "redis_id")
    private String redisId;

    @Column(name = "login")
    private String login;

    @Column(name = "hash_password")
    private String hashPassword;

    @Column(name = "name")
    private String name;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "state")
    private State state;

    public enum Role {
        USER, ADMIN
    }

    public enum State {
        ACTIVE, BANNED
    }

}
