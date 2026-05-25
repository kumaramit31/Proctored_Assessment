package com.udacity.jdnd.course3.critter.user;

import org.springframework.web.bind.annotation.*;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private EmployeeService employeeService;
    /*@PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        throw new UnsupportedOperationException();
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        throw new UnsupportedOperationException();
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        throw new UnsupportedOperationException();
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        throw new UnsupportedOperationException();
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        throw new UnsupportedOperationException();
    }

}*/
    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer saved = customerService.save(convertToCustomerEntity(customerDTO));
        return convertToCustomerDTO(saved);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        return customerService.findAll().stream()
                .map(this::convertToCustomerDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {
        return convertToCustomerDTO(customerService.findByPetId(petId));
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee saved = employeeService.save(convertToEmployeeEntity(employeeDTO));
        return convertToEmployeeDTO(saved);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return convertToEmployeeDTO(employeeService.findById(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable,
                                @PathVariable long employeeId) {
        employeeService.setAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        return employeeService
                .findAvailableForService(employeeDTO.getDate(), employeeDTO.getSkills())
                .stream()
                .map(this::convertToEmployeeDTO)
                .collect(Collectors.toList());
    }

    private CustomerDTO convertToCustomerDTO(Customer c) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setPhoneNumber(c.getPhoneNumber());
        dto.setNotes(c.getNotes());
        if (c.getPets() != null) {
            dto.setPetIds(c.getPets().stream()
                    .map(p -> p.getId())
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    private Customer convertToCustomerEntity(CustomerDTO dto) {
        Customer c = new Customer();
        c.setName(dto.getName());
        c.setPhoneNumber(dto.getPhoneNumber());
        c.setNotes(dto.getNotes());
        return c;
    }

    private EmployeeDTO convertToEmployeeDTO(Employee e) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setSkills(e.getSkills());
        // Return null if empty — test asserts getDaysAvailable() is null before setAvailability()
        dto.setDaysAvailable(e.getDaysAvailable().isEmpty() ? null : e.getDaysAvailable());
        return dto;
    }

    private Employee convertToEmployeeEntity(EmployeeDTO dto) {
        Employee e = new Employee();
        e.setName(dto.getName());
        if (dto.getSkills() != null) e.setSkills(dto.getSkills());
        if (dto.getDaysAvailable() != null) e.setDaysAvailable(dto.getDaysAvailable());
        return e;
    }
}
