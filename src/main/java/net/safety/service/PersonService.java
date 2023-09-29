package net.safety.service;

import net.safety.dto.PersonDto;
import net.safety.dto.PersonDtoConverter;
import net.safety.dto.PersonUpdateRequest;
import net.safety.exception.DataLoadErrorException;
import net.safety.exception.PersonAlreadyExistException;
import net.safety.exception.PersonNotFoundException;
import net.safety.model.Person;
import net.safety.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonDtoConverter personDtoConverter;
    private final Logger logger = LoggerFactory.getLogger(PersonService.class);


    public PersonService(PersonRepository personRepository, PersonDtoConverter personDtoConverter) {
        this.personRepository = personRepository;
        this.personDtoConverter = personDtoConverter;
    }


    public Set<PersonDto> getAllPersons() {
        logger.info("getAllPersons started.");
        try {
            return PersonRepository.listPersons.stream()
                    .map(personDtoConverter::convert).collect(Collectors.toSet());
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
        return personDtoConverter.convert(personFind);
    }

    public PersonDto createPerson(Person from) {
        Person personToCreate;
        try {
           personToCreate = personRepository.createPerson(from);
        }catch (PersonAlreadyExistException e){
            throw new PersonAlreadyExistException("A person with same name and last name exists already !");
        }
        return personDtoConverter.convert(personToCreate);
    }

    public PersonDto updatePerson(String firstName, String lastName, PersonUpdateRequest personUpdateRequest) {
        try {
            return personDtoConverter.convert(personRepository.updatePerson(firstName,lastName,personUpdateRequest));
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
}
