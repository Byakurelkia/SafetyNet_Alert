package net.safety.dataLoad;

import com.jsoniter.any.Any;
import net.safety.model.FireStation;
import net.safety.model.MedicalRecord;
import net.safety.model.Person;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataLoadPerson {

    private final static DataLoadInit allData = new DataLoadInit();
    private final DataLoadMedicalRecord dataLoadMedicalRecord;
    private final DataLoadFireStation dataLoadFireStation;

    Any allDataFromJSON = allData.readerFileJSON();

    public DataLoadPerson(DataLoadMedicalRecord dataLoadMedicalRecord, DataLoadFireStation dataLoadFireStation) throws IOException {
        this.dataLoadMedicalRecord = dataLoadMedicalRecord;
        this.dataLoadFireStation = dataLoadFireStation;
    }

    public Set<Person> allPersons(){
            Any personAny = allDataFromJSON.get("persons");
            Set<Person> personsSet = new HashSet<>();

            personAny.forEach(pp->
                    personsSet.add(
                        new Person(
                                    pp.get("firstName").toString(),
                                    pp.get("lastName").toString(),
                                    pp.get("address").toString(),
                                    pp.get("city").toString(),
                                    pp.get("email").toString(),
                                    pp.get("phone").toString(),
                                    pp.get("zip").toString()
                        )
                    )
            );
            return personsSet;
    }

    public Set<Person> allPersonsWithMedicalRecord(){
        Any medicalRecord = allDataFromJSON.get("medicalrecords");
        Set<Person> personsWithMedicalRecords = new HashSet<>(allPersons());
        List<MedicalRecord> allMedicalRecords = dataLoadMedicalRecord.allMedicalRecords();

        personsWithMedicalRecords.forEach(p->
                {
                    String name = p.getFirstName();
                    String lastName = p.getLastName();
                    allMedicalRecords.forEach(m ->{
                        if(m.getLastName().equals(lastName) && m.getFirstName().equals(name))
                            p.setMedicalRecords(m);
                        }
                    );
                }
        );
        return personsWithMedicalRecords;
    }

    public Set<Person> allPersonsWithMedRecAndFireStation(){
        Set<FireStation>  allFireStations = dataLoadFireStation.allFireStations();
        Set<Person> personFullInfo = new HashSet<>(allPersonsWithMedicalRecord());

        personFullInfo.forEach(p->
                {
                    String address = p.getAdress();
                    allFireStations.forEach(f->
                            {
                                if (f.getAddress().equals(p.getAdress()))
                                    p.setFireStation(f);
                            }
                    );
                }
        );
        return personFullInfo;
    }




}
