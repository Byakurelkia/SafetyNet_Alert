package net.safety.service;

import net.safety.dto.MedicalRecordDto;
import net.safety.exception.MedicalRecordAlreadyExistException;
import net.safety.exception.MedicalRecordNotFoundException;
import net.safety.model.MedicalRecord;
import net.safety.repository.MedicalRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class MedicalRecordServiceTest {

    @InjectMocks
    private MedicalRecordService medicalRecordService;

    @Mock
    MedicalRecordRepository medicalRecordRepository;

    @Test
    void get_all_med_rec_should_return_list(){
        MedicalRecord medicalRecord1 = new MedicalRecord("firstname1", "lastname1", new Date(),
                Arrays.asList("medication1"), Set.of("allergies1"));
        MedicalRecord medicalRecord2 = new MedicalRecord("firstname2", "lastname2", new Date(),
                Arrays.asList("medication2"), Set.of("allergies2"));

        List<MedicalRecord> medicalRecordList = new ArrayList<>();
        medicalRecordList.add(medicalRecord1);
        medicalRecordList.add(medicalRecord2);

        Mockito.when(medicalRecordRepository.getAllMedicalRecords()).thenReturn(medicalRecordList);

        List<MedicalRecord> receivedResult = medicalRecordService.getAllMedicalRecords();

        Mockito.verify(medicalRecordRepository).getAllMedicalRecords();

        assertTrue(receivedResult.size()>1);
        assertEquals(medicalRecordList, receivedResult);

    }

    @Test
    void get_med_rec_by_first_and_last_name_should_return_medical_record(){
        String firstName = "firstName";
        String lastName = "lastName";
        Set<MedicalRecord> medicalRecordSet = new HashSet<>();
        MedicalRecord medRecExpected = new MedicalRecord("firstName","lastName",
                new Date(), Arrays.asList("medications"), Set.of());
        medicalRecordSet.add(medRecExpected);

        Mockito.when(medicalRecordRepository.getMedicalRecordByNameAndLastName(firstName,lastName)).thenReturn(medicalRecordSet);

        Set<MedicalRecord> resultMedRecSetReceived = medicalRecordService.getMedicalRecordByNameAndLastName(firstName,lastName);

        Mockito.verify(medicalRecordRepository).getMedicalRecordByNameAndLastName(firstName,lastName);
        assertEquals(medicalRecordSet, resultMedRecSetReceived);
    }

    @Test
    void get_med_rec_by_first_and_last_name_should_throw_exception(){
        String firstName = "firstName";
        String lastName = "lastName";
        Mockito.when(medicalRecordRepository.getMedicalRecordByNameAndLastName(firstName,lastName))
                .thenThrow(MedicalRecordNotFoundException.class);

        MedicalRecordNotFoundException exception = assertThrows(MedicalRecordNotFoundException.class,
                ()-> medicalRecordService.getMedicalRecordByNameAndLastName(firstName,lastName));

        assertEquals("This person doesnt have a medical record saved!" ,exception.getMessage());
    }

    @Test
    void create_med_rec_should_return_med_rec(){
        MedicalRecord medRecToCreateAndExpected = new MedicalRecord("firstName","lastName",
                new Date(), Arrays.asList("medications"), Set.of());

        Mockito.when(medicalRecordRepository.createMedicalRecord(medRecToCreateAndExpected)).thenReturn(medRecToCreateAndExpected);

        MedicalRecord resultReceived = medicalRecordService.createMedicalRecord(medRecToCreateAndExpected);

        Mockito.verify(medicalRecordRepository).createMedicalRecord(medRecToCreateAndExpected);

        assertEquals(medRecToCreateAndExpected,resultReceived);
    }

    @Test
    void create_med_rec_should_throw_exception(){
        MedicalRecord medRecToCreate = new MedicalRecord("firstName","lastName",
                new Date(), Arrays.asList("medications"), Set.of());

        Mockito.when(medicalRecordRepository.createMedicalRecord(medRecToCreate))
                .thenThrow(MedicalRecordAlreadyExistException.class);

        MedicalRecordAlreadyExistException exception = assertThrows(MedicalRecordAlreadyExistException.class,
                ()-> medicalRecordService.createMedicalRecord(medRecToCreate));

        Mockito.verify(medicalRecordRepository).createMedicalRecord(medRecToCreate);

        assertEquals("A medical record already exist for this person", exception.getMessage());
    }

    @Test
    void update_med_rec_should_return_updated_med_rec(){
        String firstName = "firstName";
        String lastName = "lastName";
        MedicalRecord medicalRecordExpected = new MedicalRecord(firstName,lastName,new Date(),
                Arrays.asList("medication 1"), Set.of("allergie 1"));

        MedicalRecordDto medicalRecordDto = new MedicalRecordDto(new Date(),
                Arrays.asList("medication 1"), Set.of("allergie 1"));

        Mockito.when(medicalRecordRepository.updateMedicalRecord(firstName,lastName,medicalRecordExpected)).thenReturn(medicalRecordExpected);

        MedicalRecord medicalRecordReceived = medicalRecordService.updateMedicalRecord(firstName,lastName,medicalRecordDto);

        Mockito.verify(medicalRecordRepository).updateMedicalRecord(firstName,lastName,medicalRecordExpected);

        assertEquals(medicalRecordExpected, medicalRecordReceived);

    }

    @Test
    void update_med_rec_should_throw_exception(){
        String firstName = "firstName";
        String lastName = "lastName";
        MedicalRecord medicalRecordExpected = new MedicalRecord(firstName,lastName,new Date(),
                Arrays.asList("medication 1"), Set.of("allergie 1"));

        MedicalRecordDto medicalRecordDto = new MedicalRecordDto(new Date(),
                Arrays.asList("medication 1"), Set.of("allergie 1"));

        Mockito.when(medicalRecordRepository.updateMedicalRecord(firstName,lastName,medicalRecordExpected))
                .thenThrow(MedicalRecordNotFoundException.class);

        MedicalRecordNotFoundException exception = assertThrows(MedicalRecordNotFoundException.class,
                ()-> medicalRecordService.updateMedicalRecord(firstName,lastName,medicalRecordDto));

        Mockito.verify(medicalRecordRepository).updateMedicalRecord(firstName,lastName,medicalRecordExpected);

        assertEquals("Medical record doesnt exist with this first and last name", exception.getMessage());
    }

    @Test
    void delete_med_rec_should_return_nothing(){
        String firstName = "firstName";
        String lastName = "lastName";

        medicalRecordService.deleteMedicalRecord(firstName,lastName);

        Mockito.verify(medicalRecordRepository).deleteMedicalRecord(firstName,lastName);
    }

    @Test
    void delete_med_rec_should_throw_exception(){
        String firstName = "firstName";
        String lastName = "lastName";

        Mockito.doThrow(MedicalRecordNotFoundException.class)
                .when(medicalRecordRepository).deleteMedicalRecord(firstName,lastName);

        MedicalRecordNotFoundException exception = assertThrows(MedicalRecordNotFoundException.class,
                ()-> medicalRecordService.deleteMedicalRecord(firstName,lastName));

        Mockito.verify(medicalRecordRepository).deleteMedicalRecord(firstName,lastName);

        assertEquals("Medical record doesnt exist with this first and last name", exception.getMessage());
    }
}