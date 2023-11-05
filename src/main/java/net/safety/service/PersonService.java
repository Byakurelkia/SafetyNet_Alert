package net.safety.service;

import net.safety.dto.*;
import net.safety.exception.AddressNotFoundException;
import net.safety.exception.DataLoadErrorException;
import net.safety.exception.PersonAlreadyExistException;
import net.safety.exception.PersonNotFoundException;
import net.safety.mapper.PersonMapper;
import net.safety.model.MedicalRecord;
import net.safety.model.Person;
import net.safety.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final FireStationService fireStationService;
    private final MedicalRecordService medicalRecordService;
    private final Logger logger = LoggerFactory.getLogger(PersonService.class);


    public PersonService(PersonRepository personRepository, FireStationService fireStationService, MedicalRecordService medicalRecordService) {
        this.personRepository = personRepository;
        this.fireStationService = fireStationService;
        this.medicalRecordService = medicalRecordService;
    }

    public Set<Person> getAllPersons() {
        logger.info("getAllPersons started.");
        try {
            return personRepository.getAllPersons();
        } catch (DataLoadErrorException e){
            logger.error("Error when reading persons data from file !");
            throw new DataLoadErrorException("Error when reading persons data from file !");
        }
    }

    public Person getPersonByLastAndFirstName(String firstName, String lastName) {
        Person personFind;
        try {
             personFind = personRepository.getPersonByLastAndFirstName(firstName,lastName);
        }catch (PersonNotFoundException e){
            throw new PersonNotFoundException("Person doesn't exist with this first and last name !");
        }
        return personFind;
    }

    public List<Person> getPersonsByAddress(String address){
        try{
            return personRepository.getPersonsByAddress(address);
        }catch (PersonNotFoundException e){
            throw new PersonNotFoundException("Nobody exist in this address specified! ");
        }
    }

    public Person createPerson(Person from) {
        Person personToCreate;
        try {
           personToCreate = personRepository.createPerson(from);
        }catch (PersonAlreadyExistException e){
            throw new PersonAlreadyExistException("A person with same name and last name exists already !");
        }
        return personToCreate;
    }

    public Person updatePerson(String firstName, String lastName, PersonDto personDto) {
        try {
            return personRepository.updatePerson(firstName,lastName, PersonMapper.INSTANCE.personDtoToPerson(personDto));
        }catch (PersonNotFoundException e){
            throw new PersonNotFoundException("Person with first and last name : '"+ firstName + " , "
                    + lastName + " doesnt exists !");
        }
    }

    public void deletePerson(String firstName, String lastName) {
        try {
            personRepository.deletePerson(firstName,lastName);
        }catch (PersonNotFoundException e){
            throw new PersonNotFoundException
                    ("Person with first and last name : '"+ firstName + " , " + lastName + " doesnt exists !");
        }
    }

    /* ALERT SERVICES */

    public List<String> getAllMailsByCity(String city) {
        logger.info("Alert Service Get All Mails By City Started..");
        List<String> allMailsByCity = new ArrayList<>();
        List<Person> allPersonsByCity = personRepository.getAllPersons().stream()
                .filter(person-> person.getCity().equals(city)).collect(Collectors.toList());

        allPersonsByCity.forEach(person -> allMailsByCity.add(person.getMail()));
        if (allPersonsByCity.isEmpty())
            throw new AddressNotFoundException("This city doesnt exist in our database ! ");

        return allMailsByCity;
    }

    public List<PersonInfoByLastNameDto> getPersonsInfoByFirstAndLastNameDto(String lastName) {
        logger.info("Alert Service Get Persons Info By Last Name Dto Started..");
        return personRepository.getPersonsListByLastName(lastName).stream().map(person->{
            PersonInfoByLastNameDto personInfoDto = PersonMapper.INSTANCE.personToPersonInfoByLastNameDto(person);
            Set<MedicalRecord> medRec = medicalRecordService
                    .getMedicalRecordByNameAndLastName(person.getFirstName(), person.getLastName());

            medRec.forEach(mRecord-> {
                try {
                    personInfoDto.setAge(fireStationService.countAge(mRecord.getBirthDate()));
                    personInfoDto.setMedications(mRecord.getMedications());
                    personInfoDto.setAllergies(mRecord.getAllergies());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            return personInfoDto;
        }).collect(Collectors.toList());
    }
}
