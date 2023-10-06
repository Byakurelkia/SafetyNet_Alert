package net.safety.service;


import net.safety.dto.MedicalRecordCreateRequest;
import net.safety.dto.MedicalRecordDto;
import net.safety.dto.MedicalRecordDtoConverter;
import net.safety.dto.MedicalRecordUpdateRequest;
import net.safety.exception.DataLoadErrorException;
import net.safety.exception.MedicalRecordAlreadyExistException;
import net.safety.exception.MedicalRecordNotFoundException;
import net.safety.repository.MedicalRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final MedicalRecordDtoConverter medicalRecordDtoConverter;
    private final Logger logger = LoggerFactory.getLogger(MedicalRecordService.class);

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository, MedicalRecordDtoConverter medicalRecordDtoConverter) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.medicalRecordDtoConverter = medicalRecordDtoConverter;
    }

    public List<MedicalRecordDto> getAllMedicalRecords() {
        try {
           return medicalRecordRepository.getAllMedicalRecords().stream().map(medicalRecordDtoConverter::convertToDto)
                   .collect(Collectors.toList());
        } catch (DataLoadErrorException e) {
            logger.error("Error when medical records data loading from file JSON..");
            throw new DataLoadErrorException("Error when data loading from file..");
        }
    }

    public List<MedicalRecordDto> getMedicalRecordByNameAndLastName(String firstName, String lastName){
        try{
            return medicalRecordRepository.getMedicalRecordByNameAndLastName(firstName,lastName).stream()
                    .map(medicalRecordDtoConverter::convertToDto).collect(Collectors.toList());
        }catch(MedicalRecordNotFoundException e){
            logger.error("Medical record doesnt exist with this frist and last name");
            throw new MedicalRecordNotFoundException("This person doesnt have a medical record saved!");
        }
    }

    public MedicalRecordDto createMedicalRecord(MedicalRecordCreateRequest from){
        try {
            return medicalRecordDtoConverter.convertToDto(medicalRecordRepository
                    .createMedicalRecord(medicalRecordDtoConverter.convertToMedicalRecord(from)));
        }catch(MedicalRecordAlreadyExistException e){
            logger.error("ALREADY EXIST");
            throw new MedicalRecordAlreadyExistException("EXIST");
        }
    }

    public MedicalRecordDto updateMedicalRecord(String firstName, String lastName, MedicalRecordUpdateRequest from) {
        try {
            return medicalRecordDtoConverter.convertToDto(medicalRecordRepository.updateMedicalRecord(firstName, lastName, from));
        }catch (MedicalRecordNotFoundException e){
            logger.error("Medical record doesnt exist with this first and last name");
            throw new MedicalRecordNotFoundException("Medical record doesnt exist with this first and last nam");
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
