package ru.skypro.homework.entity;

import lombok.*;
import ru.skypro.homework.model.Role;


import javax.persistence.*;

@Entity
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String image;

    @Enumerated(EnumType.STRING)
    private Role role;

}