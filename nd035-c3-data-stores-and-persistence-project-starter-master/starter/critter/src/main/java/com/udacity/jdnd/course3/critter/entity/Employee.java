package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import jakarta.persistence.*;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="employee")
public class Employee{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    @Column(nullable=false)
    private String name;
    @ElementCollection(targetClass=EmployeeSkill.class, fetch=FetchType.EAGER)
    @CollectionTable(name="employee_skill", joinColumns=@JoinColumn(name="employee_id"))
    @Enumerated(EnumType.STRING)
    @Column(name="skill")
    private Set<EmployeeSkill> skills=new HashSet<>();

    @ElementCollection(targetClass=DayOfWeek.class, fetch=FetchType.EAGER)
    @CollectionTable(name="employee_availability", joinColumns=@JoinColumn(name="employee_id"))
    @Enumerated(EnumType.STRING)
    @Column(name="day_of_week")
    private Set<DayOfWeek> daysAvailable=new HashSet<>();

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Set<EmployeeSkill> getSkills() {
        return skills;
    }
    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }
    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }
    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }
}