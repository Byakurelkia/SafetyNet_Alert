package net.safety.dataLoad;

import com.jsoniter.any.Any;
import net.safety.model.MedicalRecord;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataLoadMedicalRecord {

    private final static DataLoadInit allData = new DataLoadInit();
    Any allDataFromJSON = allData.readerFileJSON();

    public DataLoadMedicalRecord() throws IOException {
    }

    public List<MedicalRecord> allMedicalRecords(){
        Any medRecords = allDataFromJSON.get("medicalrecords");
        List<MedicalRecord> medicalRecords = new ArrayList<>();

        medRecords.forEach(m->
                {
                    List<String> medications = new ArrayList<>();
                    Set<String> allergies = new HashSet<>();
                    for(Any med : m.get("medications").asList())
                        medications.add(med.toString());

                    for (Any allergie : m.get("allergies").asList())
                        allergies.add(allergie.toString());

                    try {
                        medicalRecords.add(
                                new MedicalRecord(
                                        m.get("firstName").toString(),
                                        m.get("lastName").toString(),
                                        new SimpleDateFormat("dd/MM/yyyy").parse(m.get("birthdate").toString()),
                                        medications,
                                        allergies
                                )
                        );
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                );
        return medicalRecords;
    }
}
