package net.safety.rest_Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import net.safety.model.FireStation;
import net.safety.response.ChildAndFamilyByAddressResponse;
import net.safety.response.PersonInfoByStationNumberResponse;
import net.safety.response.PersonsByAddressResponse;
import net.safety.response.PersonsInfoByStationNumberListResponse;
import net.safety.service.FireStationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/firestations")
public class FireStationController {

    private final FireStationService fireStationService;

    public FireStationController(FireStationService fireStationService){
        this.fireStationService = fireStationService;
    }

    //OK
    @Operation(summary = "Request to get all fire stations")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Fire Stations load successfully"),
                    @ApiResponse(responseCode = "500", description = "Error when loading data source")})
    @GetMapping
    public ResponseEntity<Set<FireStation>> getAllStation() {
        return ResponseEntity.ok(fireStationService.getAllFireStations());
    }

    //OK
    @Operation(summary = "Request to get a fire station with station number")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Fire Station successfully loaded"),
                    @ApiResponse(responseCode = "404", description = "Fire station doesnt exist with this number") })
    @GetMapping("/find/{stationNumber}")
    public ResponseEntity<List<FireStation>> getFireStationByNumber(@PathVariable("stationNumber") int stationNumber){
        return ResponseEntity.ok(fireStationService.getFireStationByNumber(stationNumber));
    }

    //OK
    @Operation(summary = "Request to create a new fire station")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Fires station created successfully"),
                    @ApiResponse(responseCode = "400", description = "Fire station is already exist")})
    @PostMapping
    public ResponseEntity<FireStation> createFireStation(@RequestBody FireStation fireStation) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fireStationService.saveFireStation(fireStation));
    }

    //OK
    @Operation(summary = "Request toupdate a firestation ")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Fire station updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Fire station doesnt exist with this address") })
    @PutMapping("/{address}")
    public ResponseEntity<FireStation> updateFireStationNumberOfAnAddress(@PathVariable("address") String address, @RequestBody int number) {
        FireStation fireStation = new FireStation(address,number);
        return ResponseEntity.ok(fireStationService.updateFireStation(fireStation));
    }

    //OK
    @Operation(summary = "Request to delete a firestation")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Fire station deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Fire station doesnt exist with this address and number") })
    @DeleteMapping
    public ResponseEntity<Void> deleteFireStationFromAnAddress(@RequestParam(name = "address") String address, @RequestParam(name = "stationNumber") int number){
        fireStationService.deleteFireStationForAnAddress(address,number);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    /* ALERT RESPONSE URL PART*/

    //OK
    @Operation(summary = "Request to get allperson info by station number")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Person Info for station specified loaded successfully"),
            @ApiResponse(responseCode = "404", description = "This station number doesnt exist! ")})
    @GetMapping("/{stationNumber}")
    public ResponseEntity<PersonInfoByStationNumberResponse> getPersonInfoByStationNumber(
            @PathVariable("stationNumber") int stationNumber){
        return ResponseEntity.ok(fireStationService.getPersonInfoByStationNumber(stationNumber));
    }

    //OK
    @Operation(summary = "Request to get all child and family persons list")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Childs and family loaded successfully"),
            @ApiResponse(responseCode = "404", description = "This address doesnt exist on our database! ")})
    @GetMapping("/childAlert/{address}")
    public ResponseEntity<ChildAndFamilyByAddressResponse> getChildAndFamilyByAddress(
            @PathVariable(value = "address") String address){
        return ResponseEntity.ok(fireStationService.getChildAndFamilyByAddress(address));
    }

    //OK
    @Operation(summary = "Request to get all phone numbers handed by a station specified")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Phone numbers loaded successfully"),
            @ApiResponse(responseCode = "404", description = "This station number doesnt exist!")})
    @GetMapping("phoneAlert/{stationNumber}")
    public ResponseEntity<Set<String>> getAllPhoneNumbersByStationNumber(
            @PathVariable(value = "stationNumber") int stationNumber){
        return ResponseEntity.ok(fireStationService.getAllPhoneNumbersByStationNumber(stationNumber));
    }

    //OK
    @Operation(summary = "Request to get all persons info by address")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Persons info loaded successfully"),
            @ApiResponse(responseCode = "404", description = "This address doesnt exist on our database ! ")})
    @GetMapping("/fire/{address}")
    public ResponseEntity<PersonsByAddressResponse> getPersonsInfoByAddress(
            @PathVariable(value = "address") String address){
        return ResponseEntity.ok(fireStationService.getPersonsInfoByAddress(address));
    }

    //OK
    @Operation(summary = "Request to get all persons info for a list of station specified")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Persons info loaded successfully"),
            @ApiResponse(responseCode = "404", description = "This station number doesnt exist on our database ! ")})
    @GetMapping("/flood/{listStationNumber}")
    public ResponseEntity<List<PersonsInfoByStationNumberListResponse>> getPersonsInfoByStationNumberList(
            @PathVariable(value = "listStationNumber") List<Integer> listNumbers){
        return ResponseEntity.ok(fireStationService.getPersonsInfoByStationNumberList(listNumbers));
    }

}
