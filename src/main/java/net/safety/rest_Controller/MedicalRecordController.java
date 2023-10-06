package net.safety.rest_Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import net.safety.dto.MedicalRecordCreateRequest;
import net.safety.dto.MedicalRecordDto;
import net.safety.dto.MedicalRecordUpdateRequest;
import net.safety.service.MedicalRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicalrecords")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;
    private final Logger logger = LoggerFactory.getLogger(MedicalRecordController.class);

    public MedicalRecordController(MedicalRecordService medicalRecordService){
        this.medicalRecordService = medicalRecordService;
    }


    @Operation(summary = "Request to get all medical records")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Medical records loaded successfully"),
                    @ApiResponse(responseCode = "500", description = "Error when loading data file")})
    @GetMapping
    public ResponseEntity<List<MedicalRecordDto>> getAllMedRec(){
        return ResponseEntity.ok(medicalRecordService.getAllMedicalRecords());
    }


    @Operation(summary = "Request to get a medical record for person specified")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",description = "Medical record loaded successfully"),
                    @ApiResponse(responseCode = "404",description = "Medical record doesnt exist for this person")})
    @GetMapping("/{firstName}/{lastName}")
    public List<MedicalRecordDto> getMedicalRecordsByNameAndLastName(@PathVariable(value = "firstName") String firstName,
                                                                  @PathVariable(value = "lastName") String lastName){
            return medicalRecordService.getMedicalRecordByNameAndLastName(firstName,lastName);
    }


    @Operation(summary = "Request to create a new medical record - Enter the date of birth as follows : year-month-day")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Medical record record created successfully"),
                    @ApiResponse(responseCode = "400", description = "A medical record already exist for this person")})
    @PostMapping
    public ResponseEntity<MedicalRecordDto> createMedicalRecord(@RequestBody MedicalRecordCreateRequest from){
        return ResponseEntity.status(HttpStatus.CREATED).body(medicalRecordService.createMedicalRecord(from));
    }



    @Operation(summary = "Request to update a medical record ")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Medical record updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Medical record doesnt exist for person specified")})
    @PutMapping("/{firstName}/{lastName}")
    public ResponseEntity<MedicalRecordDto> updateMedicalRecord(@PathVariable(value = "firstName") String firstName,
                                                                @PathVariable(value = "lastName") String lastName,
                                                                @RequestBody MedicalRecordUpdateRequest from){
        return ResponseEntity.ok(medicalRecordService.updateMedicalRecord(firstName, lastName, from));
    }



    @Operation(summary = "Request to delete a medical record")
    @ApiResponses(value = {@ApiResponse(responseCode = "204",description = "Medical record deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Medical record doesnt exist !")})
    @DeleteMapping("/{firstName}/{lastName}")
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable(value = "firstName") String firstName,
                                                    @PathVariable(value = "lastName") String lastName){
        medicalRecordService.deleteMedicalRecord(firstName,lastName);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
