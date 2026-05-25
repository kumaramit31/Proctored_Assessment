package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.web.bind.annotation.*;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertToEntity(scheduleDTO);
        Schedule saved = scheduleService.save(schedule,
                scheduleDTO.getEmployeeIds(), scheduleDTO.getPetIds());
        return convertToDTO(saved);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return scheduleService.findByPet(petId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return scheduleService.findByEmployee(employeeId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return scheduleService.findByCustomer(customerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ScheduleDTO convertToDTO(Schedule s) {
        ScheduleDTO dto = new ScheduleDTO();
        dto.setId(s.getId());
        dto.setDate(s.getDate());
        dto.setActivities(s.getActivities());
        dto.setEmployeeIds(s.getEmployees().stream()
                .map(e -> e.getId()).collect(Collectors.toList()));
        dto.setPetIds(s.getPets().stream()
                .map(p -> p.getId()).collect(Collectors.toList()));
        return dto;
    }

    private Schedule convertToEntity(ScheduleDTO dto) {
        Schedule s = new Schedule();
        s.setDate(dto.getDate());
        s.setActivities(dto.getActivities());
        return s;
    }
}