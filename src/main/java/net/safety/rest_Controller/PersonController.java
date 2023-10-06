package net.safety.rest_Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import net.safety.dto.PersonCreateRequest;
import net.safety.dto.PersonDto;
import net.safety.dto.PersonUpdateRequest;
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
    public ResponseEntity<Set<PersonDto>> allPersons(){
        return ResponseEntity.ok(personService.getAllPersonsDto());
    }

    //OK
    @Operation(summary="Request to get one person")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Person is loaded successfully"),
            @ApiResponse(responseCode = "404", description = "Person doesn't exist with this name and lastname")})
    @GetMapping("/persons/find/{firstName}/{lastName}")
    public ResponseEntity<PersonDto> getPersonByLastAndFirstName(@PathVariable(name = "firstName") String firstName,
                                                 @PathVariable(name = "lastName") String lastName){
        return ResponseEntity.ok(personService.getPersonByLastAndFirstName(firstName,lastName));
    }

    //OK
    @Operation(summary = "Request to create a new person")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Person is created successfully"),
            @ApiResponse(responseCode ="400", description = "A person is already exists with this name and lastname")})
    @PostMapping("/persons")
    public ResponseEntity<PersonDto> createPerson(@RequestBody PersonCreateRequest from){
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.createPerson(from));
    }

    //OK
    @Operation(summary = "Request to update a person ")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Person is updated successfully"),
            @ApiResponse(responseCode = "404", description = "A person doesn't exist with this name and lastname")})
    @PutMapping("/persons/{firstName}/{lastName}")
    public ResponseEntity<PersonDto> updatePerson(@PathVariable(name = "firstName") String firstName,
                                  @PathVariable(name = "lastName") String lastName,
                                  @RequestBody PersonUpdateRequest personUpdateRequest){
        return ResponseEntity.ok(personService.updatePerson(firstName, lastName, personUpdateRequest));
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


/*
    @Operation(summary = "Request to get all phone numbers for a station number")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Numbers get successfully"),
                    @ApiResponse(responseCode = "404",description = "Firestation not found with this number")})
    @GetMapping("/phoneAlert/firestation={stationNumber}")
        public ResponseEntity<List<String>> getAllPhoneNumbers(@PathVariable(name = "stationNumber") int stationNumber){
        return ResponseEntity.ok(personService.getAllPhoneNumbersForAFireStation(stationNumber));
    }
*/

  /*  @GetMapping("/deneme")
    public ResponseEntity<Set<Person>> getPersonsWithAllInformations(){
        return ResponseEntity.ok(personService.getAllInfoPersons());
    }*/


}
