package net.safety.rest_Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import net.safety.dto.PersonDto;
import net.safety.dto.PersonDtoConverter;
import net.safety.dto.PersonUpdateRequest;
import net.safety.model.Person;
import net.safety.repository.PersonRepository;
import net.safety.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonRepository personRepository;
    private final PersonService personService;

    public PersonController(PersonRepository personRepository, PersonDtoConverter personDtoConverter, PersonService personService) {
        this.personRepository = personRepository;
        this.personService = personService;
    }

    //OK
    @Operation(summary="Request to get all persons")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Persons are loaded successfully")})
    @GetMapping
    public Set<PersonDto> allPersons(){
        return personService.getAllPersons();
    }

    //OK
    @Operation(summary="Request to get one person")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Person is loaded successfully"),
            @ApiResponse(responseCode = "404", description = "Person doesn't exist with this name and lastname")})
    @GetMapping("/find/{firstName}/{lastName}")
    public PersonDto getPersonByLastAndFirstName(@PathVariable(name = "firstName") String firstName,
                                                 @PathVariable(name = "lastName") String lastName){
        return personService.getPersonByLastAndFirstName(firstName,lastName);
    }

    //OK
    @Operation(summary = "Request to create a new person")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Person is created successfully"),
                    @ApiResponse(responseCode ="400", description = "A person is already exists with this name and lastname")})
    @PostMapping
    public PersonDto createPerson(@RequestBody Person from){
        return personService.createPerson(from);
    }

    //OK
    @Operation(summary = "Request to update a person ")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Person is updated successfully"),
                    @ApiResponse(responseCode = "404", description = "A person doesn't exist with this name and lastname")})
    @PutMapping("/{firstName}/{lastName}")
    public PersonDto updatePerson(@PathVariable(name = "firstName") String firstName,
                                  @PathVariable(name = "lastName") String lastName,
                                  @RequestBody PersonUpdateRequest personUpdateRequest){
        return personService.updatePerson(firstName, lastName, personUpdateRequest);
    }

    //OK
    @Operation(summary = "Request to delete a person")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Person is deleted successfully "),
                    @ApiResponse(responseCode = "404", description = "A person doesn't exist with this name and lastname")})
    @DeleteMapping("/{firstName}/{lastName}")
    public ResponseEntity<Void> deletePerson(@PathVariable(name = "firstName") String firstName,
                                                 @PathVariable(name = "lastName") String lastName){
        personService.deletePerson(firstName,lastName);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
