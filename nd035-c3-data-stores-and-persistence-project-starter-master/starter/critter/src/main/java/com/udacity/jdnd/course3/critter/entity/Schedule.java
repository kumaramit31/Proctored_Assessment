package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate date;

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
            name="schedule_employee",
            joinColumns=@JoinColumn(name="schedule_id"),
            inverseJoinColumns=@JoinColumn(name="employee_id")
    )
    private List<Employee> employees=new ArrayList<>();
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
            name="schedule_pet",
            joinColumns=@JoinColumn(name="schedule_id"),
            inverseJoinColumns=@JoinColumn(name="pet_id")
    )
    private List<Pet> pets= new ArrayList<>();
    @ElementCollection(targetClass=EmployeeSkill.class, fetch=FetchType.EAGER)
    @CollectionTable(name="schedule_activity", joinColumns=@JoinColumn(name="schedule_id"))
    @Enumerated(EnumType.STRING)
    @Column(name="activity")
    private Set<EmployeeSkill> activities=new HashSet<>();

    public long getId(){ return id;}
    public void setId(long id){ this.id=id;}
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date){ this.date=date;}
    public List<Employee> getEmployees() {
        return employees;
    }
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
    public List<Pet> getPets() {
        return pets;
    }
    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
    public Set<EmployeeSkill> getActivities() {
        return activities;
    }
    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }
}