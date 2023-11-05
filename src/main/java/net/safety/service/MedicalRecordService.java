package net.safety.service;

import net.safety.dto.MedicalRecordDto;
import net.safety.exception.DataLoadErrorException;
import net.safety.exception.MedicalRecordAlreadyExistException;
import net.safety.exception.MedicalRecordNotFoundException;
import net.safety.model.MedicalRecord;
import net.safety.repository.MedicalRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final Logger logger = LoggerFactory.getLogger(MedicalRecordService.class);

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public List<MedicalRecord> getAllMedicalRecords() {
        try {
           return medicalRecordRepository.getAllMedicalRecords();
        } catch (DataLoadErrorException e) {
            logger.error("Error when medical records data loading from file JSON..");
            throw new DataLoadErrorException("Error when data loading from file..");
        }
    }

    public Set<MedicalRecord> getMedicalRecordByNameAndLastName(String firstName, String lastName){
        try{
            return medicalRecordRepository.getMedicalRecordByNameAndLastName(firstName,lastName);
        }catch(MedicalRecordNotFoundException e){
            logger.error("Medical record doesnt exist with this frist and last name");
            throw new MedicalRecordNotFoundException("This person doesnt have a medical record saved!");
        }
    }

    public MedicalRecord createMedicalRecord(MedicalRecord from){
        try {
            return medicalRecordRepository.createMedicalRecord(from);
        }catch(MedicalRecordAlreadyExistException e){
            logger.error("ALREADY EXIST");
            throw new MedicalRecordAlreadyExistException("A medical record already exist for this person");
        }
    }

    public MedicalRecord updateMedicalRecord(String firstName, String lastName, MedicalRecordDto from) {
        try {
            MedicalRecord medicalRecord = new MedicalRecord(firstName,lastName,from.getBirthDate(), from.getMedications(), from.getAllergies());
            return medicalRecordRepository.updateMedicalRecord(
                    firstName,
                    lastName,
                    medicalRecord);
        }catch (MedicalRecordNotFoundException e){
            logger.error("Medical record doesnt exist with this first and last name");
            throw new MedicalRecordNotFoundException("Medical record doesnt exist with this first and last name");
        }
    }

    public void deleteMedicalRecord(String firstName, String lastName){
        try {
            medicalRecordRepository.deleteMedicalRecord(firstName,lastName);
        }catch (MedicalRecordNotFoundException e){
            throw new MedicalRecordNotFoundException("Medical record doesnt exist with this first and last name");
        }
    }
}
