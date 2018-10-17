package ru.pb.springstart.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Pavel Barmyonkov on 11.10.18.
 * pbarmenkov@gmail.com
 */


@Entity
@Table
public class Office {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message="Обязательно для заполнения.")
    @Column(unique = true, nullable = false)
    private String name;

    @NotEmpty(message="Обязательно для заполнения.")
    @Column(nullable = false)
    private String address;

    @OneToMany(mappedBy = "office", fetch = FetchType.EAGER)
    private List<Subdivision> subdivisions;

    public Office() {
    }

    public Office(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Subdivision> getSubdivisions() {
        return subdivisions;
    }

    public void setSubdivisions(List<Subdivision> subdivisions) {
        this.subdivisions = subdivisions;
    }
}
