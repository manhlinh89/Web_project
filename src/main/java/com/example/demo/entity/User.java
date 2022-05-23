package com.example.demo.entity;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
@Table(name="users")
@TypeDef(
        name = "json",
        typeClass = JsonStringType.class
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "email", nullable = false, unique = true, length = 200)
    private String email;

    @Column(name="phone", length = 11)
    private String phone;

    @Column(name = "password", nullable = false)
    private String password;

    //@Column(name="name")
   // private String name;
    @Column(name = "full_name", nullable = false, length = 200)
    private String fullName;

    @Column(name = "address")
    private String address;

    @Column(name = "status", columnDefinition = "BOOLEAN")
    private boolean status;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Type(type = "json")
    @Column(name = "roles", nullable = false, columnDefinition = "json")
    //@Column(name = "role", nullable = false, columnDefinition = "varchar(255) default 'USER'")
    private List<String> roles;

    public User(long id) {
        this.id = id;
    }
}
