package net.safety.repository;

import com.jsoniter.any.Any;
import net.safety.dataLoad.DataLoadInit;
import net.safety.exception.DataLoadErrorException;
import net.safety.model.MedicalRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

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


            allMedicalRecordsFromJSON.forEach(any ->{
                List<String> medications = new ArrayList<>();
                Set<String> allergies = new HashSet<>();
                        any.get("medications").forEach(med-> medications.add(med.toString()));
                        any.get("allergies").forEach(all-> allergies.add(all.toString()));
                        listMedicalRecords.add(
                                new MedicalRecord(
                                        any.get("firstName").toString(),
                                        any.get("lastName").toString(),
                                        new Date(Date.parse(any.get("birthdate").toString())),
                                        medications,
                                        Set.copyOf(allergies)
                                )
                        );

                    }
            );
        }catch (DataLoadErrorException | IOException e){
            logger.error("Error when reading data from file..");
            throw new DataLoadErrorException("Error when reading data from file ..");
        }

    }


}
