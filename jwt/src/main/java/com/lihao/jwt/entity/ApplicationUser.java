package com.lihao.jwt.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
@Table(name = "t_user")
public class ApplicationUser {
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    @Id
    @Column(name = "userid", nullable = false)
    private long userId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    ////    @GeneratedValue(strategy = GenerationType.IDENTITY)

}
