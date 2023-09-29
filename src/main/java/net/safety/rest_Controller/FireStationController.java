package net.safety.rest_Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import net.safety.dto.FireStationDto;
import net.safety.model.FireStation;
import net.safety.repository.FireStationRepository;
import net.safety.service.FireStationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/firestations")
public class FireStationController {

    private final FireStationRepository fireStationRepository;
    private final FireStationService fireStationService;

    public FireStationController(FireStationRepository fireStationRepository, FireStationService fireStationService) throws IOException {
        this.fireStationRepository = fireStationRepository;
        this.fireStationService = fireStationService;
    }

    //OK
    @Operation(summary = "Request to get all fire stations")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Fire Stations load successfully"),
                    @ApiResponse(responseCode = "500", description = "Error when loading data source")})
    @GetMapping
    public ResponseEntity<Set<FireStationDto>> getAllStation() throws IOException {
        return ResponseEntity.ok(fireStationService.getAllFireStationsDto());
    }

    //OK
    @Operation(summary = "Request to get a fire station with station number")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Fire Station successfully loaded"),
                    @ApiResponse(responseCode = "404", description = "Fire station doesnt exist with this number") })
    @GetMapping("/find/{stationNumber}")
    public ResponseEntity<List<FireStationDto>> getFireStationByNumber(@PathVariable("stationNumber") int stationNumber){
        return ResponseEntity.ok(fireStationService.getFireStationByNumber(stationNumber));
    }

    //OK
    @Operation(summary = "Request to create a new fire station")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Fires station created successfully"),
                    @ApiResponse(responseCode = "400", description = "Fire station is already exist")})
    @PostMapping
    public ResponseEntity<FireStationDto> createFireStation(@RequestBody FireStation fireStation) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fireStationService.saveFireStation(fireStation));
    }

    //OK
    @Operation(summary = "Request toupdate a firestation ")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Fire station updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Fire station doesnt exist with this address") })
    @PutMapping("{address}")
    public ResponseEntity<FireStationDto> updateFireStationNumberOfAnAddress(@PathVariable("address") String address, @RequestBody int number) {
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



}
