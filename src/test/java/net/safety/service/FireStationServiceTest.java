package net.safety.service;

import net.safety.dto.ChildAndFamilyByAddressDto;
import net.safety.dto.PersonInfoByStationNumberDto;
import net.safety.dto.PersonInfoDto;
import net.safety.dto.PersonsInfoByAddressOrStationNumberDto;
import net.safety.exception.AddressNotFoundException;
import net.safety.exception.FireStationIsAlreadyExistException;
import net.safety.exception.FireStationNotFoundException;
import net.safety.exception.PersonNotFoundException;
import net.safety.mapper.PersonInfoByStationNumberMapper;
import net.safety.mapper.PersonMapper;
import net.safety.model.FireStation;
import net.safety.model.MedicalRecord;
import net.safety.model.Person;
import net.safety.repository.FireStationRepository;
import net.safety.repository.PersonRepository;
import net.safety.response.ChildAndFamilyByAddressResponse;
import net.safety.response.PersonInfoByStationNumberResponse;
import net.safety.response.PersonsByAddressResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FireStationServiceTest {

    @InjectMocks
    private FireStationService fireStationService;
    @Mock
    FireStationRepository fireStationRepository;
    @Mock
    PersonService personService;
    @Mock
    MedicalRecordService medicalRecordService;

    PersonRepository personRepository = Mockito.mock(PersonRepository.class);

    @Test
    public void should_return_set_of_firestation() {
        Set<FireStation> allFireStationsSet = new HashSet<>();
        FireStation f1 = new FireStation("1509 Culver St", 3);
        allFireStationsSet.add(f1);
        FireStation f2 = new FireStation("29 15th St", 2);
        allFireStationsSet.add(f2);
        FireStation f3 = new FireStation("834 Binoc Ave", 3);
        allFireStationsSet.add(f3);
        FireStation f4 = new FireStation("644 Gershwin Cir", 1);
        allFireStationsSet.add(f4);
        FireStation f5 = new FireStation("748 Townings Dr", 3);
        allFireStationsSet.add(f5);
        FireStation f6 = new FireStation("112 Steppes Pl", 3);
        allFireStationsSet.add(f6);
        FireStation f7 = new FireStation("489 Manchester St", 4);
        allFireStationsSet.add(f7);
        FireStation f8 = new FireStation("892 Downing Ct", 2);
        allFireStationsSet.add(f8);
        FireStation f9 = new FireStation("908 73rd St", 1);
        allFireStationsSet.add(f9);
        FireStation f10 = new FireStation("112 Steppes Pl", 4);
        allFireStationsSet.add(f10);
        FireStation f11 = new FireStation("947 E. Rose Dr", 1);
        allFireStationsSet.add(f11);
        FireStation f12 = new FireStation("748 Townings Dr", 3);
        allFireStationsSet.add(f12);
        FireStation f13 = new FireStation("951 LoneTree Rd", 2);
        allFireStationsSet.add(f13);


        Mockito.when(fireStationRepository.getAllFireStations()).thenReturn(allFireStationsSet);

        Set<FireStation> result = fireStationService.getAllFireStations(); //retourne null

        Mockito.verify(fireStationRepository).getAllFireStations();
        assertEquals(allFireStationsSet, result);

    }

    @Test
    public void create_firestation_should_return_firestation() {
        FireStation fireStationToCreate = new FireStation("ADDRESSE ESSAI", 19);

        Mockito.when(fireStationRepository.addForeStation(fireStationToCreate)).thenReturn(fireStationToCreate);

        FireStation result = fireStationService.saveFireStation(fireStationToCreate);

        Mockito.verify(fireStationRepository).addForeStation(fireStationToCreate); //retourne null

        assertEquals(fireStationToCreate, result);
    }

    @Test
    public void create_firestation_should_return_an_exception() {
        FireStation fireStationToCreate = new FireStation("1509 Culver St", 3);

        Mockito.when(fireStationRepository.addForeStation(fireStationToCreate))
                .thenThrow(FireStationIsAlreadyExistException.class);

        FireStationIsAlreadyExistException exception =
                assertThrows(FireStationIsAlreadyExistException.class,
                        () -> fireStationService.saveFireStation(fireStationToCreate));

        Mockito.verify(fireStationRepository).addForeStation(fireStationToCreate);
        assertEquals("Same Firestation exists", exception.getMessage());

    }

    @Test
    public void update_firestation_should_return_firestation() {
        FireStation fireStationToUpdate = new FireStation("1509 Culver St", 15);

        Mockito.when(fireStationRepository.updateFireStation(fireStationToUpdate)).thenReturn(fireStationToUpdate);

        FireStation result = fireStationService.updateFireStation(fireStationToUpdate);

        Mockito.verify(fireStationRepository).updateFireStation(fireStationToUpdate);
        assertEquals(fireStationToUpdate, result);

    }

    @Test
    public void update_firestation_should_return_an_exception() {
        FireStation fireStationToUpdate = new FireStation("1509 Culver St", 15);

        Mockito.when(fireStationRepository.updateFireStation(fireStationToUpdate))
                .thenThrow(AddressNotFoundException.class);

        AddressNotFoundException exception = assertThrows(AddressNotFoundException.class,
                () -> fireStationService.updateFireStation(fireStationToUpdate));

        Mockito.verify(fireStationRepository).updateFireStation(fireStationToUpdate);
        assertEquals("L'adresse spécifié n'existe pas ! ", exception.getMessage());
    }

    @Test
    public void delete_firestation_should_return_nothing() {
        String address = "ESSAI ADDRESS";
        int stationNumber = 89;

        fireStationService.deleteFireStationForAnAddress(address, stationNumber);

        Mockito.verify(fireStationRepository).deleteFireStationForAnAddress(address, stationNumber);
    }

    @Test
    public void delete_firestation_should_return_an_exception() {
        String address = "ESSAI ADDRESS";
        int stationNumber = 89;

        Mockito.doThrow(FireStationNotFoundException.class)
                .when(fireStationRepository).deleteFireStationForAnAddress(address, stationNumber);

        FireStationNotFoundException exception =
                assertThrows(FireStationNotFoundException.class,
                        () -> fireStationService.deleteFireStationForAnAddress(address, stationNumber));

        Mockito.verify(fireStationRepository).deleteFireStationForAnAddress(address, stationNumber);
        assertEquals("This address and number specified doesn't correspond to a fire station ! ", exception.getMessage());

    }

    @Test
    public void get_firestation_by_number_should_return_list() {
        List<FireStation> fireStationList = new ArrayList<>();
        FireStation f1 = new FireStation("1509 Culver St", 3);
        FireStation f2 = new FireStation("29 15th St", 3);
        FireStation f3 = new FireStation("834 Binoc Ave", 3);
        fireStationList.add(f1);
        fireStationList.add(f2);
        fireStationList.add(f3);

        Mockito.when(fireStationRepository.getFireStationByNumber(3)).thenReturn(fireStationList);
        List<FireStation> resultList = fireStationService.getFireStationByNumber(3);

        Mockito.verify(fireStationRepository).getFireStationByNumber(3);
        assertEquals(fireStationList, resultList);

    }

    @Test
    public void get_firestation_by_number_should_thrown_exception() {

        Mockito.when(fireStationRepository.getFireStationByNumber(3)).thenThrow(FireStationNotFoundException.class);

        FireStationNotFoundException exception = assertThrows(FireStationNotFoundException.class,
                ()-> fireStationService.getFireStationByNumber(3));

        Mockito.verify(fireStationRepository).getFireStationByNumber(3);
        assertEquals("FireStation specified with this number : " + 3 + " doesn't exist.", exception.getMessage());

    }

    @Test
    public void get_person_info_by_station_number_should_return_personsList_with_info() throws ParseException {
        List<FireStation> fireStationList = new ArrayList<>();
        FireStation f1 = new FireStation("1509 Culver St", 3);
        FireStation f2 = new FireStation("29 15th St", 3);
        fireStationList.add(f1);
        fireStationList.add(f2);

        Set<Person> allPersons = new HashSet<>();
        Person p1 = new Person("firstname","lastname","1509 Culver St","city","mail","9876543","78570");
        Person p2 = new Person("2nd firstname","2nd lastname","29 15th St","2nd city","2ndmail","2nd 9876543","2nd 78570");
        allPersons.add(p1);
        allPersons.add(p2);

        List<PersonInfoByStationNumberDto> personInfoByStationNumberDtos = new ArrayList<>();
        PersonInfoByStationNumberDto pDto1 = PersonInfoByStationNumberMapper.INSTANCE.personInfoByStationNumberDto(p1);
        PersonInfoByStationNumberDto pDto2 = PersonInfoByStationNumberMapper.INSTANCE.personInfoByStationNumberDto(p2);
        personInfoByStationNumberDtos.add(pDto1);
        personInfoByStationNumberDtos.add(pDto2);

        List<MedicalRecord> medicalRecordList = new ArrayList<>();
        List<String> medications = Arrays.asList("doliprane", "nurofen");
        Set<String> allergies = new HashSet<>();
        allergies.add("niaciline");
        allergies.add("amoprex");
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String date = "01-01-1990";
        String date2 = "01-01-2010";
        MedicalRecord m1 = new MedicalRecord("firstname","lastname", format.parse(date), medications,allergies);
        MedicalRecord m2 = new MedicalRecord("2nd firstname","2nd lastname", format.parse(date2), medications,allergies);
        medicalRecordList.add(m1);
        medicalRecordList.add(m2);

        Mockito.when(fireStationService.getFireStationByNumber(3)).thenReturn(fireStationList);
        Mockito.when(personService.getAllPersons()).thenReturn(allPersons);
        Mockito.when(medicalRecordService.getAllMedicalRecords()).thenReturn(medicalRecordList);

        String expectedResult = new PersonInfoByStationNumberResponse(personInfoByStationNumberDtos, 1,1).toString();
        String result = fireStationService.getPersonInfoByStationNumber(3).toString();

        Mockito.verify(personService, Mockito.times(1)).getAllPersons();
        Mockito.verify(medicalRecordService).getAllMedicalRecords();

        assertEquals(expectedResult, result);



    }

    @Test
    public void get_person_info_by_station_number_should_return_empty_personsList() throws ParseException {
        List<FireStation> fireStationList = new ArrayList<>();
        FireStation f1 = new FireStation("1509 Culver St", 3);
        FireStation f2 = new FireStation("29 15th St", 3);
        fireStationList.add(f1);
        fireStationList.add(f2);

        Set<Person> allPersons = new HashSet<>();
        Person p1 = new Person("firstname","lastname","Culver St","city","mail","9876543","78570");
        Person p2 = new Person("2nd firstname","2nd lastname","15th St","2nd city","2ndmail","2nd 9876543","2nd 78570");
        allPersons.add(p1);
        allPersons.add(p2);

        List<PersonInfoByStationNumberDto> personInfoByStationNumberDtos = new ArrayList<>();

        List<MedicalRecord> medicalRecordList = new ArrayList<>();
        List<String> medications = Arrays.asList("doliprane", "nurofen");
        Set<String> allergies = new HashSet<>();
        allergies.add("niaciline");
        allergies.add("amoprex");
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String date = "01-01-1990";
        String date2 = "01-01-2010";
        MedicalRecord m1 = new MedicalRecord("firstname","lastname", format.parse(date), medications,allergies);
        MedicalRecord m2 = new MedicalRecord("2nd firstname","2nd lastname", format.parse(date2), medications,allergies);
        medicalRecordList.add(m1);
        medicalRecordList.add(m2);

        Mockito.when(fireStationService.getFireStationByNumber(3)).thenReturn(fireStationList);
        Mockito.when(personService.getAllPersons()).thenReturn(allPersons);
        Mockito.when(medicalRecordService.getAllMedicalRecords()).thenReturn(medicalRecordList);

        String expectedResult = new PersonInfoByStationNumberResponse(personInfoByStationNumberDtos, 0,0).toString();
        String result = fireStationService.getPersonInfoByStationNumber(3).toString();

        Mockito.verify(personService, Mockito.times(1)).getAllPersons();
        Mockito.verify(medicalRecordService).getAllMedicalRecords();

        assertEquals(expectedResult, result);

    }

    @Test
    public void get_person_info_by_station_number_should_thrown_exception(){
        Mockito.when(fireStationRepository.getFireStationByNumber(36)).thenThrow(FireStationNotFoundException.class);

        assertThrows(FireStationNotFoundException.class,
                ()-> fireStationService.getPersonInfoByStationNumber(36),
                "FireStation specified with this number : 36 doesn't exist.");

        Mockito.verify(fireStationRepository).getFireStationByNumber(36);
    }

    @Test
    public void get_child_and_family_by_address_should_return_list_with_child() throws ParseException {
        String address = "1509 Culver St";
        List<Person> personList = new ArrayList<>();
        Person p1 = new Person("firstname","lastname","1509 Culver St","city","mail","9876543","78570");
        Person p2 = new Person("child1","child1lastName","1509 Culver St","city","mail","9876543","78570");
        Person p3 = new Person("firstname3","lastname3","1509 Culver St","city","mail","9876543","78570");
        Person p4 = new Person("child2","child2lastName","1509 Culver St","city","mail","9876543","78570");
        personList.add(p1);
        personList.add(p2);
        personList.add(p3);
        personList.add(p4);

        List<String> medications = Arrays.asList("doliprane", "nurofen");
        Set<String> allergies = new HashSet<>();
        allergies.add("niaciline");
        allergies.add("amoprex");
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String date = "01-01-1990";
        String date2 = "01-01-2010";
        String date3 = "01-01-2000";
        String date4 = "01-01-2020";

        Set<MedicalRecord> medicalRecordSet1 = new HashSet<>();
        medicalRecordSet1.add(new MedicalRecord("firstname","lastname", format.parse(date), medications,allergies));
        Set<MedicalRecord> medicalRecordSet2 = new HashSet<>();
        medicalRecordSet2.add(new MedicalRecord("child1","child1lastName", format.parse(date2), medications,allergies));
        Set<MedicalRecord> medicalRecordSet3 = new HashSet<>();
        medicalRecordSet3.add(new MedicalRecord("firstname3","lastname3", format.parse(date3), medications,allergies));
        Set<MedicalRecord> medicalRecordSet4 = new HashSet<>();
        medicalRecordSet4.add(new MedicalRecord("child2","child2lastName", format.parse(date4), medications,allergies));


        List<PersonInfoDto> familyList = Arrays.asList(
                PersonMapper.INSTANCE.personToPersonInfoDto(p1),
                PersonMapper.INSTANCE.personToPersonInfoDto(p3));

        List<ChildAndFamilyByAddressDto> childDtoList = Arrays.asList(
                new ChildAndFamilyByAddressDto("child1","child1lastName",13),
                new ChildAndFamilyByAddressDto("child2","child2lastName",3));


        Mockito.when(personService.getPersonsByAddress(address)).thenReturn(personList);
        Mockito.when(medicalRecordService.getMedicalRecordByNameAndLastName("firstname","lastname")).thenReturn(medicalRecordSet1);
        Mockito.when(medicalRecordService.getMedicalRecordByNameAndLastName("child1","child1lastName")).thenReturn(medicalRecordSet2);
        Mockito.when(medicalRecordService.getMedicalRecordByNameAndLastName("firstname3","lastname3")).thenReturn(medicalRecordSet3);
        Mockito.when(medicalRecordService.getMedicalRecordByNameAndLastName("child2","child2lastName")).thenReturn(medicalRecordSet4);

        String expectedResult = new ChildAndFamilyByAddressResponse(childDtoList, familyList).toString();
        String result = fireStationService.getChildAndFamilyByAddress(address).toString();

        Mockito.verify(personService).getPersonsByAddress(address);
        Mockito.verify(medicalRecordService, Mockito.times(4)).getMedicalRecordByNameAndLastName(Mockito.anyString(), Mockito.anyString());

        assertEquals(expectedResult,result);

    }

    @Test
    public void get_child_and_family_by_address_should_return_list_without_child() throws ParseException {
        String address = "29 15th St";
        List<Person> personList = new ArrayList<>();
        Person p1 = new Person("firstname","lastname","1509 Culver St","city","mail","9876543","78570");
        Person p2 = new Person("firstname3","lastname3","1509 Culver St","city","mail","9876543","78570");
        personList.add(p1);
        personList.add(p2);

        List<String> medications = Arrays.asList("doliprane", "nurofen");
        Set<String> allergies = new HashSet<>();
        allergies.add("niaciline");
        allergies.add("amoprex");
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String date = "01-01-1990";
        String date2 = "01-01-2000";

        Set<MedicalRecord> medicalRecordSet1 = new HashSet<>();
        medicalRecordSet1.add(new MedicalRecord("firstname","lastname", format.parse(date), medications,allergies));
        Set<MedicalRecord> medicalRecordSet2 = new HashSet<>();
        medicalRecordSet2.add(new MedicalRecord("firstname3","lastname3", format.parse(date2), medications,allergies));

        List<PersonInfoDto> familyList = Arrays.asList(
                PersonMapper.INSTANCE.personToPersonInfoDto(p1),
                PersonMapper.INSTANCE.personToPersonInfoDto(p2));

        List<ChildAndFamilyByAddressDto> childDtoList = Arrays.asList();

        Mockito.when(personService.getPersonsByAddress(address)).thenReturn(personList);
        Mockito.when(medicalRecordService.getMedicalRecordByNameAndLastName("firstname","lastname")).thenReturn(medicalRecordSet1);
        Mockito.when(medicalRecordService.getMedicalRecordByNameAndLastName("firstname3","lastname3")).thenReturn(medicalRecordSet2);

        String expectedResult = new ChildAndFamilyByAddressResponse(childDtoList, familyList).toString();
        String result = fireStationService.getChildAndFamilyByAddress(address).toString();

        Mockito.verify(personService).getPersonsByAddress(address);
        Mockito.verify(medicalRecordService, Mockito.times(2)).getMedicalRecordByNameAndLastName(Mockito.anyString(), Mockito.anyString());

        assertEquals(expectedResult,result);

    }

    @Test
    public void get_child_and_family_by_address_should_return_exception(){
        String address = "0";

        Mockito.when(personService.getPersonsByAddress(address)).thenThrow(PersonNotFoundException.class);

        assertThrows(PersonNotFoundException.class,
                ()-> fireStationService.getChildAndFamilyByAddress(address),
                "Nobody exist in this address specified! ");
    }

    @Test
    public void get_all_phone_numbers_should_return_list_numbers(){
        String adres1 = "1509 Culver St";
        String adres2 = "29 15th St";
        List<FireStation> fireStationList = new ArrayList<>();
        FireStation f1 = new FireStation("1509 Culver St", 7);
        FireStation f2 = new FireStation("29 15th St", 7);
        fireStationList.add(f1);
        fireStationList.add(f2);

        List<Person> personListAdressOne = new ArrayList<>();
        List<Person> personListAdress2 = new ArrayList<>();

        Person p1 = new Person("firstPersonFirstName","firstPersonLastName","1509 Culver St","city","mail","987456","78570");
        Person p2 = new Person("secondPersonFirstName","secondPersonFirstName","1509 Culver St","city","mail","123456","78570");
        Person p3 = new Person("thirdPersonFirstName","thirdPersonFirstName","29 15th St","city","mail","0000000","78570");
        personListAdressOne.add(p1);
        personListAdressOne.add(p2);
        personListAdress2.add(p3);

        Mockito.when(fireStationRepository.getFireStationByNumber(7)).thenReturn(fireStationList);
        Mockito.when(personService.getPersonsByAddress(adres1)).thenReturn(personListAdressOne);
        Mockito.when(personService.getPersonsByAddress(adres2)).thenReturn(personListAdress2);

        List<String> expectedResult = Arrays.asList("987456","123456","0000000");
        List<String> result = fireStationService.getAllPhoneNumbersByStationNumber(7);

        Mockito.verify(fireStationRepository).getFireStationByNumber(7);
        Mockito.verify(personService, Mockito.times(2)).getPersonsByAddress(Mockito.anyString());

        assertEquals(expectedResult,result);

    }

    @Test
    public void get_person_info_by_address_should_return_person_list_information() throws ParseException {
        String address = "1509 Culver St";

        List<Person> personList= new ArrayList<>();
        Person p1 = new Person("firstPersonFirstName","firstPersonLastName","1509 Culver St","city","mail","987456","78570");
        Person p2 = new Person("secondPersonFirstName","secondPersonLastName","1509 Culver St","city","mail","123456","78570");
        personList.add(p1);
        personList.add(p2);

        List<FireStation> fireStationList = new ArrayList<>();
        FireStation f1 = new FireStation("1509 Culver St", 7);
        FireStation f2 = new FireStation("1509 Culver St", 3);
        fireStationList.add(f1);
        fireStationList.add(f2);

        List<String> medications = Arrays.asList("doliprane", "nurofen");
        Set<String> allergies = new HashSet<>();
        allergies.add("niaciline");
        allergies.add("amoprex");
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String date = "01-01-1990";
        String date2 = "01-11-2023";

        Set<MedicalRecord> medicalRecordSet1 = new HashSet<>();
        medicalRecordSet1.add(new MedicalRecord("firstPersonFirstName","firstPersonLastName", format.parse(date), medications,allergies));
        Set<MedicalRecord> medicalRecordSet2 = new HashSet<>();
        medicalRecordSet2.add(new MedicalRecord("secondPersonFirstName","secondPersonLastName", format.parse(date2), medications,allergies));

        List<PersonsInfoByAddressOrStationNumberDto> allPersonsByAdressDto = Arrays.asList(
                new PersonsInfoByAddressOrStationNumberDto("firstPersonLastName","987456",33,medications,allergies),
                new PersonsInfoByAddressOrStationNumberDto("secondPersonLastName","123456",0,medications,allergies)
        );

        List<Integer> stationNumbersForAddress = Arrays.asList(7,3);

        Mockito.when(personService.getPersonsByAddress(address)).thenReturn(personList);
        Mockito.when(fireStationRepository.getFireStationByAddress(address)).thenReturn(Optional.of(fireStationList));
        Mockito.when(medicalRecordService.getMedicalRecordByNameAndLastName("firstPersonFirstName","firstPersonLastName")).thenReturn(medicalRecordSet1);
        Mockito.when(medicalRecordService.getMedicalRecordByNameAndLastName("secondPersonFirstName","secondPersonLastName")).thenReturn(medicalRecordSet2);

        String expectedResult = new PersonsByAddressResponse(allPersonsByAdressDto,stationNumbersForAddress).toString();
        String result = fireStationService.getPersonsInfoByAddress(address).toString();

        Mockito.verify(personService).getPersonsByAddress(address);
        Mockito.verify(fireStationRepository).getFireStationByAddress(address);
        Mockito.verify(medicalRecordService, Mockito.times(2)).getMedicalRecordByNameAndLastName(Mockito.anyString(), Mockito.anyString());

        assertEquals(expectedResult, result);
    }

    @Test
    public void count_age_should_return_exception_for_birthdate() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String date = "01-11-2025";

        Exception exception = assertThrows(Exception.class,
                ()-> fireStationService.countAge(format.parse(date)));

        assertEquals("Birthdate year cant be superior to actual year", exception.getMessage());
    }

    @Test
    public void get_person_info_by_station_number_should_return_persons_info_list(){

    }
}