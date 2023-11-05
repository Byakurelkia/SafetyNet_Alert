package net.safety.service;


import net.safety.dto.*;
import net.safety.exception.AddressNotFoundException;
import net.safety.exception.DataLoadErrorException;
import net.safety.exception.FireStationIsAlreadyExistException;
import net.safety.exception.FireStationNotFoundException;
import net.safety.mapper.FireStationMapper;
import net.safety.mapper.PersonInfoByStationNumberMapper;
import net.safety.mapper.PersonMapper;
import net.safety.model.FireStation;
import net.safety.model.MedicalRecord;
import net.safety.model.Person;
import net.safety.repository.FireStationRepository;
import net.safety.response.ChildAndFamilyByAddressResponse;
import net.safety.response.PersonsInfoByStationNumberListResponse;
import net.safety.response.PersonInfoByStationNumberResponse;
import net.safety.response.PersonsByAddressResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class FireStationService {

    private final FireStationRepository fireStationRepository;
    private final PersonService personService;
    private final MedicalRecordService medicalRecordService;
    private final static Logger logger = LoggerFactory.getLogger(FireStationService.class);

    public FireStationService(FireStationRepository fireStationRepository, @Lazy PersonService personService, @Lazy MedicalRecordService medicalRecordService) {
        this.fireStationRepository = fireStationRepository;
        this.personService = personService;
        this.medicalRecordService = medicalRecordService;
    }

    public Set<FireStation> getAllFireStations(){
        try {
            return fireStationRepository.getAllFireStations();
        }catch (DataLoadErrorException e){
            logger.error("Error when reading the data file... ");
            throw new DataLoadErrorException("Error when reading the data file... ");
        }
    }

    public FireStation saveFireStation(FireStation from) {
        try{
            return fireStationRepository
                    .addForeStation(from);
        }catch (FireStationIsAlreadyExistException e){
            logger.error("Same FireStation exists, creation failed.");
            throw new FireStationIsAlreadyExistException("Same Firestation exists");
        }
    }

    public FireStation updateFireStation(FireStation from) {
        try {
            return fireStationRepository
                    .updateFireStation(from);
        }catch (AddressNotFoundException e){
            throw new AddressNotFoundException("L'adresse spécifié n'existe pas ! ");
        }
    }

    public void deleteFireStationForAnAddress(String address, int number){
        try {
            fireStationRepository.deleteFireStationForAnAddress(address,number);
            logger.info("Deleted successfully");
        }catch (FireStationNotFoundException e){
            logger.error("Delete Failed ! ");
            throw new FireStationNotFoundException("This address and number specified doesn't correspond to a fire station ! ");
        }
    }

    public List<FireStation> getFireStationByNumber(int stationNumber) {
        try {
            return fireStationRepository.getFireStationByNumber(stationNumber);
        }catch (FireStationNotFoundException e){
            logger.error("FireStation specified with this number : " + stationNumber + " doesn't exist.");
            throw new FireStationNotFoundException("FireStation specified with this number : " + stationNumber + " doesn't exist.");
        }
    }


    //SERVICE ALERT PART//

    public PersonInfoByStationNumberResponse getPersonInfoByStationNumber(int stationNumber) {
        logger.info("Alert Service Get Persons Info By Station Number Started");
        //receuil des stations ayant le numéro spécifié
        List<FireStation> fireStationsList = getFireStationByNumber(stationNumber); //OK

        //receuil des addresses des stations receuilli
        List<String> address = new ArrayList<>();
        fireStationsList.stream().forEach(f-> {
            if (f.getStationNumber() == stationNumber){
               address.add(f.getAddress());
            }
        });

        //receuil des personnes ayant les addresses receuillis
        Set<Person> personSetList = new HashSet<>();
        personService.getAllPersons().stream().forEach(person->{
            address.stream().forEach(a -> { //OK
                if (person.getAddress().equals(a))
                    personSetList.add(person);
            });
        });

        //receuil des quantités adultes et enfant des personnes sélectionnés
        AtomicInteger nmbAdult = new AtomicInteger();
        AtomicInteger nmbChild = new AtomicInteger();
        medicalRecordService.getAllMedicalRecords().stream().forEach(m->{
            personSetList.stream().forEach(person -> {
                if (person.getFirstName().equals(m.getFirstName()) && person.getLastName().equals(m.getLastName()))
                {
                  if (isAdult(m.getBirthDate()))
                      nmbAdult.getAndIncrement();
                  else
                      nmbChild.getAndIncrement();
                }
            });
        });

        return new PersonInfoByStationNumberResponse(
                personSetList.stream().map(p-> {
                    PersonInfoByStationNumberDto personDto = PersonInfoByStationNumberMapper
                            .INSTANCE.personInfoByStationNumberDto(p);
                    return personDto;
                }).collect(Collectors.toList())
                , nmbAdult.intValue()
                , nmbChild.intValue());

    }

    public ChildAndFamilyByAddressResponse getChildAndFamilyByAddress(String address) {
        logger.info("Alert Service Get Child And Family List By Address Started");
        List<ChildAndFamilyByAddressDto> childDtoList = new ArrayList<>();
        List<PersonInfoDto> familyList = new ArrayList<>();
        List<Person> personsList = personService.getPersonsByAddress(address);

        AtomicBoolean childPresent = new AtomicBoolean(false);


        //parcourt la liste pour determiner les enfants
        personsList.forEach(p->{

            Set<MedicalRecord> medRecList = medicalRecordService
                   .getMedicalRecordByNameAndLastName(p.getFirstName(),p.getLastName());

            //controle si la personne en question est adulte ou enfant
            medRecList.forEach(m-> {
                ChildAndFamilyByAddressDto childFound = new ChildAndFamilyByAddressDto();
               if (!isAdult(m.getBirthDate())){
                   childFound.setFirstName(p.getFirstName());
                   childFound.setLastName(p.getLastName());
                   try {
                       childFound.setAge(countAge(m.getBirthDate()));
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
                   childPresent.set(true);
                   childDtoList.add(childFound);
               }
            });
        });

        //suppression de l'enfant de la liste des personnes à cette addresse
        for (int i = 0; i < childDtoList.size(); i++) {
            Iterator<Person> iterator= personsList.iterator();
            while (iterator.hasNext()){
                Person personToDelete = iterator.next();
                if (personToDelete.getFirstName().equals(childDtoList.get(i).getFirstName())
                        && personToDelete.getLastName().equals(childDtoList.get(i).getLastName())){
                    iterator.remove();
                    break;
                }
            }
        }

        //création de la liste family
        personsList.forEach(p-> familyList.add(PersonMapper.INSTANCE.personToPersonInfoDto(p)));

        return new ChildAndFamilyByAddressResponse(childDtoList,familyList);
    }

    public List<String> getAllPhoneNumbersByStationNumber(int stationNumber) {
        logger.info("Alert Service Get All Phone Numbers By Station Number Started");
        List<String> allPhoneNumbers = new ArrayList<>();
        List<FireStation> fireStation = fireStationRepository.getFireStationByNumber(stationNumber);

            //ajout des numéros de téléphones pris en charge par la station sur la liste
        for (int i = 0; i < fireStation.size(); i++) {
            List<Person> allPersons = personService.getPersonsByAddress(fireStation.get(i).getAddress());
            allPersons.forEach(person -> allPhoneNumbers.add(person.getPhoneNumber()));
        }

        return allPhoneNumbers;
    }

    public PersonsByAddressResponse getPersonsInfoByAddress(String address) {
        logger.info("Alert Service Get Persons Info By Address Started");
        List<Person> allPersonsByAddress = personService.getPersonsByAddress(address);
        List<Integer> stationNumbersForAddress = new ArrayList<>();
        List<FireStation> allStationsByAddress = fireStationRepository.getFireStationByAddress(address).get();

        //ajout des numéros de stations à la liste int
        allStationsByAddress.forEach(station -> stationNumbersForAddress.add(station.getStationNumber()));

        //Création de la liste des personnes avec les informations demandées
        List<PersonsInfoByAddressOrStationNumberDto> allPersonsByAdressDto =
                allPersonsByAddress.stream().map(person->{
                    PersonsInfoByAddressOrStationNumberDto personsByAddressDto = PersonMapper.INSTANCE.personToPersonByAddressDto(person);

                    Set<MedicalRecord> medRecForPerson = medicalRecordService
                    .getMedicalRecordByNameAndLastName(person.getFirstName(), person.getLastName());

                    medRecForPerson.forEach(med->{
                        personsByAddressDto.setAllergies(med.getAllergies());
                        personsByAddressDto.setMedications(med.getMedications());
                        try {
                            personsByAddressDto.setAge(countAge(med.getBirthDate()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    return personsByAddressDto;
                }).collect(Collectors.toList());

        return new PersonsByAddressResponse(allPersonsByAdressDto, stationNumbersForAddress);
    }

    public List<PersonsInfoByStationNumberListResponse> getPersonsInfoByStationNumberList(List<Integer> listNumbers) {
        logger.info("Alert Service Get Persons Info List By Station Number");
        //Utilisation de set pour prévenir les doublons d'addresses qui sont pris en charge par plusieurs stations
        Set<String> addressList = new HashSet<>();
        List<PersonsInfoByStationNumberListResponse> personsInfoByStationNumberListResponseList = new ArrayList<>();

        //Récupération des addresses prise en charge par les stations donnés en paramétre
        for (int i = 0; i < listNumbers.size(); i++) {
            List<FireStation> fireStationList = fireStationRepository.getFireStationByNumber(listNumbers.get(i));
            for (int j = 0; j < fireStationList.size(); j++) {
                addressList.add(fireStationList.get(j).getAddress());
            }
        }

        //Création de la liste de reponse
        Iterator<String> iterator = addressList.iterator();
        while (iterator.hasNext()){
            String address = iterator.next();
            personsInfoByStationNumberListResponseList
                    .add(new PersonsInfoByStationNumberListResponse(
                            address,
                            personService.getPersonsByAddress(address).stream().map(person-> {
                                //Création de la personne DTO
                                PersonsInfoByAddressOrStationNumberDto personsByAddressDto = PersonMapper
                                        .INSTANCE.personToPersonByAddressDto(person);

                                //Récupération du medical record pour la personne pour l'age et les med & allergies
                                Set<MedicalRecord> medRecForPerson = medicalRecordService
                                        .getMedicalRecordByNameAndLastName(person.getFirstName(), person.getLastName());

                                medRecForPerson.forEach(med->{
                                    personsByAddressDto.setAllergies(med.getAllergies());
                                    personsByAddressDto.setMedications(med.getMedications());
                                    try {
                                        personsByAddressDto.setAge(countAge(med.getBirthDate()));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                });
                                return personsByAddressDto;
                            }).collect(Collectors.toList())
                    ));
        }
        return personsInfoByStationNumberListResponseList;
    }



    protected int countAge(Date birthdate) throws Exception {
        int age = 0;
        Date now = new Date();

        if (now.getYear() >= birthdate.getYear()){
            if (now.getMonth() > birthdate.getMonth())
                age = now.getYear() - birthdate.getYear() ;

            else if (now.getMonth() == birthdate.getMonth()){
                if (now.getDate() > birthdate.getDate() || now.getDate() == birthdate.getDate())
                    age = now.getYear() - birthdate.getYear();
            }else
                age = now.getYear() - birthdate.getYear() - 1;
        }else
            throw new Exception("Birthdate year cant be superior to actual year");

        logger.debug("Age counted is " + age);
        return age;
    }

    private boolean isAdult(Date birth){

        boolean isAdult = false;
        Date now = new Date();

        if (now.getYear() - birth.getYear() > 18)
            isAdult = true;
        else if ((now.getYear() - birth.getYear()) == 18){
            if (now.getMonth() > birth.getMonth())
                isAdult = true;
            else if (now.getDate() >= birth.getDate())
                isAdult = true;
        }
        return isAdult;
    }



}
