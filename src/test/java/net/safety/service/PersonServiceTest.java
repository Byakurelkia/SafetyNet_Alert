package net.safety.service;

import net.safety.dto.PersonDto;
import net.safety.dto.PersonInfoByLastNameDto;
import net.safety.exception.AddressNotFoundException;
import net.safety.exception.DataLoadErrorException;
import net.safety.exception.PersonAlreadyExistException;
import net.safety.exception.PersonNotFoundException;
import net.safety.mapper.PersonMapper;
import net.safety.model.MedicalRecord;
import net.safety.model.Person;
import net.safety.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @InjectMocks
    private PersonService personService;

    @Mock
    PersonRepository personRepository;

    @Mock
    FireStationService fireStationService;

    @Mock
    MedicalRecordService medicalRecordService;

    @Test
    void get_all_persons_should_return_list(){
        Set<Person> personList = Set.of(
                new Person("firstname", "lastname", "address", "city", "mail", "phonenumber", "zip"),
                new Person("firstname2", "lastname2", "address2", "city2", "mail2", "phonenumber2", "zip2")
        );

        Mockito.when(personRepository.getAllPersons()).thenReturn(personList);

        Set<Person> resultPersonList = personService.getAllPersons();

        Mockito.verify(personRepository).getAllPersons();

        assertEquals(personList,resultPersonList);
        assertTrue(resultPersonList.size()>1);
    }

    @Test
    void get_all_persons_should_throw_exception(){

        Mockito.when(personRepository.getAllPersons()).thenThrow(DataLoadErrorException.class);

        DataLoadErrorException exception = assertThrows(DataLoadErrorException.class,
                ()-> personService.getAllPersons());

        Mockito.verify(personRepository).getAllPersons();

        assertEquals("Error when reading persons data from file !", exception.getMessage());
    }

    @Test
    void get_person_by_first_and_last_name_should_return_person(){
        Person personExpected = new Person("firstname", "lastname", "address",
                "city", "mail", "phonenumber", "zip");

        Mockito.when(personRepository.getPersonByLastAndFirstName("firstname","lastname")).thenReturn(personExpected);

        Person resultReceived = personService.getPersonByLastAndFirstName("firstname","lastname");

        Mockito.verify(personRepository).getPersonByLastAndFirstName("firstname","lastname");

        assertEquals(personExpected, resultReceived);

    }

    @Test
    void get_person_by_first_and_last_name_should_throw_exception(){
        String firstName = "firstName";
        String lastName = "lastName";

        Mockito.when(personRepository.getPersonByLastAndFirstName(firstName,lastName)).thenThrow(PersonNotFoundException.class);

        PersonNotFoundException exception = assertThrows(PersonNotFoundException.class,
                ()-> personService.getPersonByLastAndFirstName(firstName,lastName));

        Mockito.verify(personRepository).getPersonByLastAndFirstName(firstName,lastName);

        assertEquals("Person doesn't exist with this first and last name !", exception.getMessage());
    }

    @Test
    void get_person_list_by_address_should_return_list_of_person(){
        List<Person> personList = Arrays.asList(
                new Person("firstname", "lastname", "address", "city", "mail", "phonenumber", "zip"),
                new Person("firstname2", "lastname2", "address2", "city2", "mail2", "phonenumber2", "zip2")
        );

        Mockito.when(personRepository.getPersonsByAddress(Mockito.anyString())).thenReturn(personList);

        List<Person> personListResultReceived = personService.getPersonsByAddress(Mockito.anyString());

        Mockito.verify(personRepository).getPersonsByAddress(Mockito.anyString());

        assertEquals(personList, personListResultReceived);
    }

    @Test
    void get_person_list_by_address_should_throw_exception(){

        Mockito.when(personRepository.getPersonsByAddress(Mockito.anyString())).thenThrow(PersonNotFoundException.class);

        PersonNotFoundException exception = assertThrows(PersonNotFoundException.class,
                ()-> personService.getPersonsByAddress(Mockito.anyString()));

        Mockito.verify(personRepository).getPersonsByAddress(Mockito.anyString());

        assertEquals("Nobody exist in this address specified! ", exception.getMessage());

    }

    @Test
    void create_person_should_return_person(){
        Person personToCreate = new Person("firstname", "lastname", "address",
                "city", "mail", "phonenumber", "zip");

        Person personExpected = new Person("firstname", "lastname", "address",
                "city", "mail", "phonenumber", "zip");

        Mockito.when(personRepository.createPerson(personToCreate)).thenReturn(personToCreate);

        Person resultPersonReceived = personService.createPerson(personToCreate);

        Mockito.verify(personRepository).createPerson(personToCreate);

        assertEquals(personExpected, resultPersonReceived);

    }

    @Test
    void create_person_should_throw_exception(){
        Person personToCreate = new Person("firstname", "lastname", "address",
                "city", "mail", "phonenumber", "zip");

        Mockito.when(personRepository.createPerson(personToCreate)).thenThrow(PersonAlreadyExistException.class);

        PersonAlreadyExistException exception = assertThrows(PersonAlreadyExistException.class,
                ()-> personService.createPerson(personToCreate));

        Mockito.verify(personRepository).createPerson(personToCreate);

        assertEquals("A person with same name and last name exists already !", exception.getMessage());
    }

    @Test
    void update_person_should_return_updated_person(){
        String firstName = "firstName";
        String lastName = "lastName";
        PersonDto personDto = new PersonDto("mail","phoneNumber","address","ZIPCODE","CITY");

        Person personUpdated = new Person("firstName","lastName","address","CITY",
                "mail","phoneNumber","ZIPCODE");

        Person personExpected = new Person(firstName,lastName,"address","CITY",
                "mail","phoneNumber","ZIPCODE");

        Mockito.when(personRepository.updatePerson(firstName,lastName, PersonMapper.INSTANCE.personDtoToPerson(personDto))).thenReturn(personUpdated);

        Person resultPersonReceived = personService.updatePerson(firstName,lastName,personDto);

        Mockito.verify(personRepository).updatePerson(firstName,lastName,PersonMapper.INSTANCE.personDtoToPerson(personDto));

        assertEquals(personExpected, resultPersonReceived);

    }

    @Test
    void update_person_should_throw_exception(){
        String firstName = "firstName";
        String lastName = "lastName";
        PersonDto personDto = new PersonDto("mail","phoneNumber","address","ZIPCODE","CITY");

        Mockito.when(personRepository.updatePerson(firstName,lastName,
                PersonMapper.INSTANCE.personDtoToPerson(personDto)))
                .thenThrow(PersonNotFoundException.class);

        PersonNotFoundException exception = assertThrows(PersonNotFoundException.class,
                ()-> personService.updatePerson(firstName,lastName,personDto));

        Mockito.verify(personRepository).updatePerson(firstName,lastName,PersonMapper.INSTANCE.personDtoToPerson(personDto));

        assertEquals("Person with first and last name : '"+ firstName + " , "
                + lastName + " doesnt exists !", exception.getMessage());

    }

    @Test
    void delete_person_should_return_nothing(){
        String firstName = "firstName";
        String lastName = "lastName";

        personService.deletePerson(firstName,lastName);

        Mockito.verify(personRepository).deletePerson(firstName,lastName);

    }

    @Test
    void delete_person_should_throw_exception(){
        String firstName = "firstName";
        String lastName = "lastName";

        Mockito.doThrow(PersonNotFoundException.class).when(personRepository).deletePerson(firstName,lastName);

        PersonNotFoundException exception = assertThrows(PersonNotFoundException.class,
                ()-> personService.deletePerson(firstName,lastName));

        Mockito.verify(personRepository).deletePerson(firstName,lastName);

        assertEquals("Person with first and last name : '"+ firstName + " , " + lastName + " doesnt exists !", exception.getMessage());

    }

    @Test
    void get_all_mails_by_city_should_return_list(){
        List<Person> personList = Arrays.asList(
                new Person("firstname1", "lastname1", "address1",
                        "city", "mail1", "phonenumber1", "zip1"),
                new Person("firstname2", "lastname2", "address2",
                        "city", "mail2", "phonenumber2", "zip2"),
                new Person("firstname3", "lastname3", "address3",
                        "city", "mail3", "phonenumber3", "zip3")
        );
        List<String> mailsList = Arrays.asList("mail1","mail2","mail3");

        Mockito.when(personRepository.getAllPersons()).thenReturn(Set.copyOf(personList));

        List<String> resultMailsReceived = personService.getAllMailsByCity("city");
        List<String> resultMailsReceivedFormatted = new ArrayList<>();

        Mockito.verify(personRepository).getAllPersons();

        for (int i = 0; i < mailsList.size(); i++) {
            if (resultMailsReceived.contains(mailsList.get(i)))
                resultMailsReceivedFormatted.add(i, mailsList.get(i));
        }

        assertEquals(mailsList, resultMailsReceivedFormatted);
    }

    @Test
    void get_all_mails_by_city_should_throw_exception(){

        Mockito.when(personRepository.getAllPersons()).thenReturn(Set.of());

        AddressNotFoundException exception = assertThrows(AddressNotFoundException.class,
                ()-> personService.getAllMailsByCity("city"));

        Mockito.verify(personRepository).getAllPersons();

        assertEquals("This city doesnt exist in our database ! ", exception.getMessage());
    }

    @Test
    void get_person_info_by_first_and_last_name_should_return_list() throws Exception {
        Person p1 = new Person("firstName 1","lastName","address 1",
                "city 1","mail 1","phoneNumber 1","zip 1");
        Person p2 = new Person("firstName 2","lastName","address 2",
                "city 2","mail 2","phoneNumber 2","zip 2");
        Person p3 = new Person("firstName 3","lastName","address 3",
                "city 3","mail 3","phoneNumber 3","zip 3");

        List<Person> personList = new ArrayList<>();
        personList.add(p1);
        personList.add(p2);
        personList.add(p3);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        List<String> medications = Arrays.asList("medication 1","medication 2");
        Set<String> allergies = Set.of("allergies 1","allergies 2");
        Set<MedicalRecord> m1 = Set.of(new MedicalRecord("firstName 1","lastName",
                df.parse("11-10-2020"), medications,allergies));
        Set<MedicalRecord> m2 = Set.of(new MedicalRecord("firstName 2","lastName",
                df.parse("11-10-2000"), medications,allergies));
        Set<MedicalRecord> m3 = Set.of(new MedicalRecord("firstName 3","lastName",
                df.parse("11-10-2015"), medications,allergies));

        List<PersonInfoByLastNameDto> personInfoByLastNameDtoListExpected = Arrays.asList(
                new PersonInfoByLastNameDto("lastName","address 1",3,"mail 1",medications,allergies),
                new PersonInfoByLastNameDto("lastName","address 2",23,"mail 2",medications,allergies),
                new PersonInfoByLastNameDto("lastName","address 3",18,"mail 3",medications,allergies)
        );

        Mockito.when(personRepository.getPersonsListByLastName("lastName")).thenReturn(personList);
        Mockito.when(medicalRecordService.getMedicalRecordByNameAndLastName("firstName 1","lastName")).thenReturn(m1);
        Mockito.when(medicalRecordService.getMedicalRecordByNameAndLastName("firstName 2","lastName")).thenReturn(m2);
        Mockito.when(medicalRecordService.getMedicalRecordByNameAndLastName("firstName 3","lastName")).thenReturn(m3);

        Mockito.when(fireStationService.countAge(df.parse("11-10-2020"))).thenReturn(3);
        Mockito.when(fireStationService.countAge(df.parse("11-10-2000"))).thenReturn(23);
        Mockito.when(fireStationService.countAge(df.parse("11-10-2015"))).thenReturn(18);

        List<PersonInfoByLastNameDto> resultListReceived = personService.getPersonsInfoByFirstAndLastNameDto("lastName");

        Mockito.verify(personRepository).getPersonsListByLastName("lastName");
        Mockito.verify(medicalRecordService, Mockito.times(3))
                .getMedicalRecordByNameAndLastName(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(fireStationService, Mockito.times(3)).countAge(Mockito.any());

        assertEquals(personInfoByLastNameDtoListExpected, resultListReceived);

    }

    @Test
    void get_person_info_by_first_and_last_name_should_throw_person_not_found_exception(){

        Mockito.when(personRepository.getPersonsListByLastName("lastName")).thenThrow(PersonNotFoundException.class);

        assertThrows(PersonNotFoundException.class,
                ()-> personService.getPersonsInfoByFirstAndLastNameDto("lastName"));
    }

}