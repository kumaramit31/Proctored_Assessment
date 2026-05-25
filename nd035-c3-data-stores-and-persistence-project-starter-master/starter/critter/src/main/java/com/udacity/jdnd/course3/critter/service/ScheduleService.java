package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PetRepository petRepository;

    public Schedule save(Schedule schedule, List<Long> employeeIds, List<Long> petIds) {
        List<Employee> employees = employeeIds.stream()
                .map(id -> employeeRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Employee not found: " + id)))
                .collect(Collectors.toList());

        List<Pet> pets = petIds.stream()
                .map(id -> petRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Pet not found: " + id)))
                .collect(Collectors.toList());

        schedule.setEmployees(employees);
        schedule.setPets(pets);
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> findByEmployee(long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + employeeId));
        return scheduleRepository.findByEmployeesContains(employee);
    }

    public List<Schedule> findByPet(long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found: " + petId));
        return scheduleRepository.findByPetsContains(pet);
    }

    public List<Schedule> findByCustomer(long customerId) {
        List<Pet> pets = petRepository.findByCustomerId(customerId);
        return pets.stream()
                .flatMap(pet -> scheduleRepository.findByPetsContains(pet).stream())
                .distinct()
                .collect(Collectors.toList());
    }
}