package net.safety.repository;

import com.jsoniter.any.Any;
import net.safety.dataLoad.DataLoadInit;
import net.safety.exception.DataLoadErrorException;
import net.safety.exception.MedicalRecordAlreadyExistException;
import net.safety.exception.MedicalRecordNotFoundException;
import net.safety.model.MedicalRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class MedicalRecordRepository {

    private final DataLoadInit dataLoadInit;
    public static List<MedicalRecord> listMedicalRecords = new ArrayList<>();
    private final Logger logger = LoggerFactory.getLogger(MedicalRecordRepository.class);

    public MedicalRecordRepository(DataLoadInit dataLoadInit){
        this.dataLoadInit = dataLoadInit;
        getAllMedicalRecordsFromFile();
    }

    //OK
    private void getAllMedicalRecordsFromFile() {
        try {
            Any allMedicalRecordsFromJSON = dataLoadInit.readerFileJSON().get("medicalrecords");
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

            allMedicalRecordsFromJSON.forEach(any ->{

                List<String> medications = new ArrayList<>();
                Set<String> allergies = new HashSet<>();
                any.get("medications").forEach(med-> medications.add(med.toString()));
                any.get("allergies").forEach(all-> allergies.add(all.toString()));

                        try {
                            listMedicalRecords.add(
                                    new MedicalRecord(
                                            any.get("firstName").toString(),
                                            any.get("lastName").toString(),
                                            df.parse(any.get("birthdate").toString()),
                                            medications,
                                            allergies
                                    )
                            );
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
            }
            );
        }catch (DataLoadErrorException e){
            logger.error("Error when reading data from file..");
            throw new DataLoadErrorException("Error when reading data from file ..");
        }

    }

    public List<MedicalRecord> getAllMedicalRecords(){
        try{
            return listMedicalRecords;
        }catch (DataLoadErrorException e){
            logger.error("Error when reading medical records data from file !");
            throw new DataLoadErrorException("Error when reading medicalrecords data from file !");
        }
    }

    public Set<MedicalRecord> getMedicalRecordByNameAndLastName(String firstName, String lastName){
        if (!isMedicalRecordExist(firstName,lastName)){
            logger.error("Medical record doesnt exist for person indicated");
            throw new MedicalRecordNotFoundException("This person doesnt have a medical record saved!");
        }

        logger.info("Medical record loaded successfully");
        return listMedicalRecords.stream()
                .filter(f-> f.getFirstName().equals(firstName) && f.getLastName().equals(lastName))
                .collect(Collectors.toSet());
    }

    public MedicalRecord createMedicalRecord(MedicalRecord from){
        if (isMedicalRecordExist(from.getFirstName(), from.getLastName())) {
            logger.error("A medical record already exist for this person , try to update it");
            throw new MedicalRecordAlreadyExistException("A medical record already exist for this person");
        }

        logger.info("Medical record created successfully");
        listMedicalRecords.add(from);
        return from;
    }

    public MedicalRecord updateMedicalRecord(String firstName, String lastName, MedicalRecord from) {
        if (!isMedicalRecordExist(firstName,lastName)){
            logger.error("Medical record doesnt exist with this first and last name");
            throw new MedicalRecordNotFoundException("Medical record doesnt exist with this first and last name");
        }

        Iterator<MedicalRecord> iterator = listMedicalRecords.iterator();
        while (iterator.hasNext()){
            MedicalRecord medicalRecordToUpdate = iterator.next();
            if (medicalRecordToUpdate.getFirstName().equals(firstName) && medicalRecordToUpdate.getLastName().equals(lastName)){
                medicalRecordToUpdate.setBirthDate(from.getBirthDate());
                medicalRecordToUpdate.setMedications(from.getMedications());
                medicalRecordToUpdate.setAllergies(from.getAllergies());
                logger.info("Medical record updated successfully");
                break;
            }
        }
        return from;
    }

    public void deleteMedicalRecord(String firstName, String lastName){
        if (!isMedicalRecordExist(firstName,lastName)){
            logger.error("Medical record delete failed");
            throw new MedicalRecordNotFoundException("Medical record doesnt exist with this first and last name");
        }

        Iterator<MedicalRecord> iterator = listMedicalRecords.iterator();
        while (iterator.hasNext()){
            MedicalRecord medicalRecordToDelete = iterator.next();
            if (medicalRecordToDelete.getFirstName().equals(firstName) && medicalRecordToDelete.getLastName().equals(lastName)){
                iterator.remove();
                logger.info("Medical record deleted successfully");
                break;
            }
        }
    }

    private boolean isMedicalRecordExist(String firstName, String lastName){
        boolean result= false;

        Iterator<MedicalRecord> iterator = listMedicalRecords.iterator();
        while (iterator.hasNext()){
            MedicalRecord medicalRecordtoSearch = iterator.next();
            if (medicalRecordtoSearch.getFirstName().equals(firstName) &&
                    medicalRecordtoSearch.getLastName().equals(lastName)){
                result = true;
                break;
            }
        }
        return result;
    }


}
