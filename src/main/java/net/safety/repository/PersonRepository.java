package net.safety.repository;


import com.jsoniter.any.Any;
import net.safety.dataLoad.DataLoadInit;
import net.safety.dto.*;
import net.safety.exception.DataLoadErrorException;
import net.safety.exception.PersonAlreadyExistException;
import net.safety.exception.PersonNotFoundException;
import net.safety.model.FireStation;
import net.safety.model.MedicalRecord;
import net.safety.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Repository
public class PersonRepository {

    public static Set<Person> listPersons = new HashSet<>();
    public static Set<Person> listPersonsWithAllInformations = new HashSet<>();
    private final DataLoadInit dataLoadInit;
    private final Logger logger = LoggerFactory.getLogger(PersonRepository.class);


    public PersonRepository(DataLoadInit dataLoadInit) {
        this.dataLoadInit = dataLoadInit;
        getAllPersonsFromFile();
    }

    private  void getAllPersonsFromFile() {
        logger.info("Reading persons from data JSON started..");
        try {
            Any allPersonsFromJSON = dataLoadInit.readerFileJSON().get("persons");
            allPersonsFromJSON.forEach(p->
            {
                listPersons.add(
                        new Person(
                                p.get("firstName").toString(),
                                p.get("lastName").toString(),
                                p.get("address").toString(),
                                p.get("city").toString(),
                                p.get("zip").toString(),
                                p.get("phone").toString(),
                                p.get("email").toString()
                        )
                );
            });
        }catch (DataLoadErrorException | IOException e){
            logger.error("Error when reading data from file..");
            e.getMessage();
            throw new DataLoadErrorException("Error when reading data from file ..");
        }
        logger.info("Persons Data reading completed successfully.");
    }

    public Set<Person> getAllPersons(){
        try {
            return listPersons;
        }catch (DataLoadErrorException e){
            logger.error("Error when reading persons data from file !");
            throw new DataLoadErrorException("Error when reading persons data from file !");
    }
    }

    public Person getPersonByLastAndFirstName(String firstName, String lastName){
        logger.info("getPersonByLastAndFirstName started..");
        Person person = getPerson(firstName,lastName);

        if (person == null){
            logger.error("Person doesn't exist with this first and lastname.");
            throw new PersonNotFoundException("Person doesn't exist with this first and lastname.");
        }
        logger.info("getPersonByLastAndFirstName realised successfully.");
        return person;
    }

    public Person createPerson(Person from) {

    if (getPerson(from.getFirstName(),from.getLastName()) == null){
        listPersons.add(from);
        logger.info("Person created successfully !");
        return from;
    }else{
        logger.error("Person creation failed ! ");
        throw new PersonAlreadyExistException("Same person exists with this name and lastname !");
    }
    }

    public Person updatePerson(String firstName, String lastName, PersonUpdateRequest personUpdateRequest){
        if (getPerson(firstName,lastName) == null){
            logger.error("Update person failed ! ");
            throw new PersonNotFoundException("Person with first and last name : '"+ firstName
                    + " , " + lastName + " doesn't exists !");
        }

        Iterator<Person> iterator= listPersons.iterator();
        while (iterator.hasNext()) {
            Person personToUpdate = iterator.next();
            if (personToUpdate.getFirstName().equals(firstName) && personToUpdate.getLastName().equals(lastName)) {
                personToUpdate.setAdress(personUpdateRequest.getAdress());
                personToUpdate.setCity(personUpdateRequest.getCity());
                personToUpdate.setZipCode(personUpdateRequest.getZipCode());
                personToUpdate.setMail(personUpdateRequest.getMail());
                personToUpdate.setPhoneNumber(personUpdateRequest.getPhoneNumber());
                logger.info("Person updated successfully ! ");
                break;
            }
        }

        return getPerson(firstName,lastName);
    }

    public void deletePerson(String firstName, String lastName){
        if (getPerson(firstName,lastName) == null){
            logger.error("Person Delete failed !");
            throw new PersonNotFoundException("");
        }

        Iterator<Person> iterator= listPersons.iterator();
        while (iterator.hasNext()){
            Person personToDelete = iterator.next();
            if (personToDelete.getFirstName().equals(firstName) && personToDelete.getLastName().equals(lastName)){
                iterator.remove();
                logger.info("Person deleted successfully !");
                break;
            }
        }
    }

    private Person getPerson(String firstName,String lastName){
       Person getPerson = null;
        for (Person personFind : listPersons){
            if (personFind.getLastName().equals(lastName) && personFind.getFirstName().equals(firstName))
                getPerson = personFind;
        }
        return getPerson;
    }



    public Set<Person> personWithFullInformation(){

        Set<Person> personWithFullInformations = listPersons;

        personWithFullInformations.forEach(p->{
            FireStationRepository.listFireStations.forEach(f->{
                if (f.getAddress().equals(p.getAdress()))
                    p.addFireStation(f);
            });
        });

        personWithFullInformations.forEach(p->{
            MedicalRecordRepository.listMedicalRecords.forEach(m->{
                if (p.getFirstName().equals(m.getFirstName()) && p.getLastName().equals(m.getLastName()))
                    p.addMedicalRecords(m);
            });
        });

        return personWithFullInformations;
    }
}
