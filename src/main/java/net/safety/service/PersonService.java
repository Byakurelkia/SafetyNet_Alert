package net.safety.service;

import net.safety.dto.*;
import net.safety.exception.DataLoadErrorException;
import net.safety.exception.PersonAlreadyExistException;
import net.safety.exception.PersonNotFoundException;
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
    private final PersonDtoConverter personDtoConverter;
    private final FireStationService fireStationService;
    private final MedicalRecordService medicalRecordService;
    private final Logger logger = LoggerFactory.getLogger(PersonService.class);


    public PersonService(PersonRepository personRepository, PersonDtoConverter personDtoConverter, FireStationService fireStationService, MedicalRecordService medicalRecordService) {
        this.personRepository = personRepository;
        this.personDtoConverter = personDtoConverter;
        this.fireStationService = fireStationService;
        this.medicalRecordService = medicalRecordService;
    }

    public Set<PersonDto> getAllPersonsDto() {
        logger.info("getAllPersons started.");
        try {
            return personRepository.getAllPersons().stream()
                    .map(personDtoConverter::convertToDto).collect(Collectors.toSet());
        } catch (DataLoadErrorException e){
            logger.error("Error when reading persons data from file !");
            throw new DataLoadErrorException("Error when reading persons data from file !");
        }
    }

    public PersonDto getPersonByLastAndFirstName(String firstName, String lastName) {
        Person personFind;
        try {
             personFind = personRepository.getPersonByLastAndFirstName(firstName,lastName);
        }catch (PersonNotFoundException e){
            throw new PersonNotFoundException("Person doesn't exist with this first and last name !");
        }
        return personDtoConverter.convertToDto(personFind);
    }

    public PersonDto createPerson(PersonCreateRequest from) {
        Person personToCreate;
        try {
           personToCreate = personRepository.createPerson(personDtoConverter.convertToPerson(from));
        }catch (PersonAlreadyExistException e){
            throw new PersonAlreadyExistException("A person with same name and last name exists already !");
        }
        return personDtoConverter.convertToDto(personToCreate);
    }

    public PersonDto updatePerson(String firstName, String lastName, PersonUpdateRequest personUpdateRequest) {
        try {
            return personDtoConverter.convertToDto(personRepository.updatePerson(firstName,lastName,personUpdateRequest));
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
/*
    public List<String> getAllPhoneNumbersForAFireStation(int stationNumber){
        if (fireStationService.getFireStationByNumber(stationNumber).isEmpty()){
            logger.error("Fire station with this number doesnt exist!");
        }

        List<FireStationPersonsDto> fireStationList = fireStationService.getFireStationByNumber(stationNumber);
        List<String> allPhoneNumbers = new ArrayList<>();



        return allPhoneNumbers;

    }*/

    public Set<Person> getAllInfoPersons(){
        return personRepository.personWithFullInformation();
    }


}
