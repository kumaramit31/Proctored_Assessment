package com.udacity.jdnd.course3.critter.pet;

import org.springframework.web.bind.annotation.*;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet saved = petService.save(convertToEntity(petDTO), petDTO.getOwnerId());
        return convertToDTO(saved);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return convertToDTO(petService.findById(petId));
    }

    @GetMapping
    public List<PetDTO> getPets() {
        return petService.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return petService.findByCustomerId(ownerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private PetDTO convertToDTO(Pet pet) {
        PetDTO dto = new PetDTO();
        dto.setId(pet.getId());
        dto.setType(pet.getType());
        dto.setName(pet.getName());
        dto.setBirthDate(pet.getBirthDate());
        dto.setNotes(pet.getNotes());
        if (pet.getCustomer() != null) {
            dto.setOwnerId(pet.getCustomer().getId());
        }
        return dto;
    }

    private Pet convertToEntity(PetDTO dto) {
        Pet pet = new Pet();
        pet.setType(dto.getType());
        pet.setName(dto.getName());
        pet.setBirthDate(dto.getBirthDate());
        pet.setNotes(dto.getNotes());
        return pet;
    }
}
