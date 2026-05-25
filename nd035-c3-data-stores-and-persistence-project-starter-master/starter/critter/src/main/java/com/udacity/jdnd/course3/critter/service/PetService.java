package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Pet save(Pet pet, long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found: " + customerId));
        pet.setCustomer(customer);
        Pet saved = petRepository.save(pet);

        // Keep customer's pet list in sync so CustomerDTO.petIds is always populated
        customer.getPets().add(saved);
        customerRepository.save(customer);

        return saved;
    }

    public Pet findById(long petId) {
        return petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found: " + petId));
    }

    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    public List<Pet> findByCustomerId(long customerId) {
        return petRepository.findByCustomerId(customerId);
    }
}