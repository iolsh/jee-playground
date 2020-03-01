package me.iolsh.users;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NamedQuery(name = User.FIND_USER_BY_USER_NAME,  query = "select u from User u where u.userName = :userName")
public class User {
    public static final String FIND_USER_BY_USER_NAME = "findUserByUserName";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
}
