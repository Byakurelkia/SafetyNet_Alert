package net.safety.repository;


import com.jsoniter.any.Any;
import net.safety.dataLoad.DataLoadInit;
import net.safety.exception.DataLoadErrorException;
import net.safety.exception.PersonAlreadyExistException;
import net.safety.exception.PersonNotFoundException;
import net.safety.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Repository
public class PersonRepository {

    public static Set<Person> listPersons = new HashSet<>();
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
                                p.get("email").toString(),
                                p.get("phone").toString(),
                                p.get("zip").toString()
                        )
                );
            });
        }catch (DataLoadErrorException e){
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

    public List<Person> getPersonsListByLastName(String lastName){
        logger.info("getPersonsListByLastName started..");
        AtomicBoolean isExist = new AtomicBoolean(false);
        listPersons.forEach(person->{
           if (person.getLastName().equals(lastName))
               isExist.set(true);
        });

        if (!isExist.get()){
            logger.error("Person doesn't exist with this lastName.");
            throw new PersonNotFoundException("Person doesn't exist with this lastName.");
        }
        logger.info("Persons list with lastName searching is complete successfully");
        return listPersons.stream().filter(person -> person.getLastName().equals(lastName)).collect(Collectors.toList());

    }

    public List<Person> getPersonsByAddress(String address){
        List<Person> personList = listPersons.stream().filter(f-> f.getAddress().equals(address)).collect(Collectors.toList());

        if (personList.isEmpty()){
            logger.error("Persons doesnt exist with this address");
            throw new PersonNotFoundException("Nobody exist in this address specified! ");
        }
        return personList;
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

    public Person updatePerson(String firstName, String lastName, Person person){
        if (getPerson(firstName,lastName) == null){
            logger.error("Update person failed ! ");
            throw new PersonNotFoundException("Person with first and last name : '"+ firstName
                    + " , " + lastName + " doesn't exists !");
        }

        Iterator<Person> iterator= listPersons.iterator();
        while (iterator.hasNext()) {
            Person personToUpdate = iterator.next();
            if (personToUpdate.getFirstName().equals(firstName) && personToUpdate.getLastName().equals(lastName)) {
                personToUpdate.setAddress(person.getAddress());
                personToUpdate.setCity(person.getCity());
                personToUpdate.setZipCode(person.getZipCode());
                personToUpdate.setMail(person.getMail());
                personToUpdate.setPhoneNumber(person.getPhoneNumber());
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
                if (f.getAddress().equals(p.getAddress()))
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
