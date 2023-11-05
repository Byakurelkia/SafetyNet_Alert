package net.safety.rest_Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import net.safety.dto.PersonDto;
import net.safety.dto.PersonInfoByLastNameDto;
import net.safety.model.Person;
import net.safety.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    //OK
    @Operation(summary="Request to get all persons")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Persons are loaded successfully"),
            @ApiResponse(responseCode = "500", description = "Error when loading data from file")})
    @GetMapping("/persons")
    public ResponseEntity<Set<Person>> allPersons(){
        return ResponseEntity.ok(personService.getAllPersons());
    }

    //OK
    @Operation(summary="Request to get one person")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Person is loaded successfully"),
            @ApiResponse(responseCode = "404", description = "Person doesn't exist with this name and lastname")})
    @GetMapping("/persons/find/{firstName}/{lastName}")
    public ResponseEntity<Person> getPersonByLastAndFirstName(@PathVariable(name = "firstName") String firstName,
                                                 @PathVariable(name = "lastName") String lastName){
        return ResponseEntity.ok(personService.getPersonByLastAndFirstName(firstName,lastName));
    }

    //OK
    @Operation(summary = "Request to create a new person")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Person is created successfully"),
            @ApiResponse(responseCode ="400", description = "A person is already exists with this name and lastname")})
    @PostMapping("/persons")
    public ResponseEntity<Person> createPerson(@RequestBody Person from){
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.createPerson(from));
    }

    //OK
    @Operation(summary = "Request to update a person ")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Person is updated successfully"),
            @ApiResponse(responseCode = "404", description = "A person doesn't exist with this name and lastname")})
    @PutMapping("/persons/{firstName}/{lastName}")
    public ResponseEntity<Person> updatePerson(@PathVariable(name = "firstName") String firstName,
                                  @PathVariable(name = "lastName") String lastName,
                                  @RequestBody PersonDto person){
        return ResponseEntity.ok(personService.updatePerson(firstName, lastName, person));
    }

    //OK
    @Operation(summary = "Request to delete a person")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Person is deleted successfully "),
            @ApiResponse(responseCode = "404", description = "A person doesn't exist with this name and lastname")})
    @DeleteMapping("/persons/{firstName}/{lastName}")
    public ResponseEntity<Void> deletePerson(@PathVariable(name = "firstName") String firstName,
                                                 @PathVariable(name = "lastName") String lastName){
        personService.deletePerson(firstName,lastName);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/personsByAddress/{address}")
    public List<Person> getPersonsByAddress(@PathVariable(value = "address") String address){
        return personService.getPersonsByAddress(address);
    }


    /*ALERT RESPONSE URL PART*/

    //OK
    @Operation(summary = "Request to get all mails for a city")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Mails loaded successfully"),
            @ApiResponse(responseCode = "404", description = "This city doesn't exist in our database! ")})
    @GetMapping("/communityEmail/{city}")
    public ResponseEntity<List<String>> getAllMailsByCity(@PathVariable(value = "city") String city){
        return ResponseEntity.ok(personService.getAllMailsByCity(city));
    }

    @Operation(summary = "Request to get specified info for lastname specified")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Persons Info loaded successfully"),
            @ApiResponse(responseCode = "404", description = "This person with lastName specified doesn't exist in our database! ")})
    @GetMapping("/personInfo/{firstName}/{lastName}")
    public ResponseEntity<List<PersonInfoByLastNameDto>> getPersonsInfoByFirstAndLastNameDto(
            @PathVariable(value = "lastName") String lastName){
        return ResponseEntity.ok(personService.getPersonsInfoByFirstAndLastNameDto(lastName));
    }


}
