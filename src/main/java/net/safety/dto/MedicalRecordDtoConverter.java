package net.safety.dto;

import net.safety.model.MedicalRecord;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class MedicalRecordDtoConverter {

    public MedicalRecordDto convertToDto(MedicalRecord from){
        return new MedicalRecordDto(
                from.getFirstName(),
                from.getLastName(),
                new SimpleDateFormat("dd/MM/yyyy").format(from.getBirthDate()),
                from.getMedications(),
                from.getAllergies()
        );
    }

    public MedicalRecord convertToMedicalRecord(MedicalRecordCreateRequest from){
        return new MedicalRecord(
                from.getFirstName(),
                from.getLastName(),
                from.getBirthDate(),
                from.getMedications(),
                from.getAllergies()
        );
    }

    public MedicalRecord convertToMedicalRecord(String firstName, String lastName, MedicalRecordUpdateRequest from){
        return new MedicalRecord(
                firstName,
                lastName,
                from.getBirthDate(),
                from.getMedications(),
                from.getAllergies()
        );
    }


}
