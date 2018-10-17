package ru.pb.springstart.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Pavel Barmyonkov on 15.10.18.
 * pbarmenkov@gmail.com
 */
@Entity
@Table
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(nullable = false)
    @NotEmpty(message="Обязательно для заполнения.")
    private String fullName;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date dateBirth;


    @Column(nullable = false)
    @NotEmpty(message="Обязательно для заполнения.")
    private String phone;

    @Email
    @Column(unique = true, nullable = false)
    @NotEmpty(message="Обязательно для заполнения.")
    private String email;

    @ManyToOne(optional = false)
    @JoinColumn(name = "subdivisionId")
    @JsonIgnore
    private Subdivision subdivision;

    public Employee() {
    }

    public Employee(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDateBirth() {

        return dateBirth;
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Subdivision getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(Subdivision subdivision) {
        this.subdivision = subdivision;
    }
}
