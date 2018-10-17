package ru.pb.springstart.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

/**
 * Created by Pavel Barmyonkov on 12.10.18.
 * pbarmenkov@gmail.com
 */

@Entity
@Table
public class Subdivision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    @NotEmpty(message = "Обязательно для заполнения.")
    private String name;

    @Column(nullable = false)
    @NotEmpty(message = "Обязательно для заполнения.")
    private String fullNameHead;

    @ManyToOne()
    @JoinColumn(name = "parentSubdivisionId")
    private Subdivision parentSubdivision;

    @OneToMany(mappedBy = "parentSubdivision",
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    private List<Subdivision> childrenSubdivision;

    @OneToMany(mappedBy = "subdivision",
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    private List<Employee> employees;

    @ManyToOne
    @JoinColumn(name = "officeId", nullable = false)
    private Office office;

    public Subdivision() {
    }

    public Subdivision(int id) {
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

    public String getFullNameHead() {
        return fullNameHead;
    }

    public void setFullNameHead(String fullNameHead) {
        this.fullNameHead = fullNameHead;
    }

    public Subdivision getParentSubdivision() {
        return parentSubdivision;
    }

    public void setParentSubdivision(Subdivision parentSubdivision) {
        this.parentSubdivision = parentSubdivision;
    }

    public List<Subdivision> getChildrenSubdivision() {
        return childrenSubdivision;
    }

    public void setChildrenSubdivision(List<Subdivision> childrenSubdivision) {
        this.childrenSubdivision = childrenSubdivision;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
