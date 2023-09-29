package net.safety.service;


import net.safety.dto.FireStationDto;
import net.safety.dto.FireStationDtoConverter;
import net.safety.exception.AddressNotFoundException;
import net.safety.exception.DataLoadErrorException;
import net.safety.exception.FireStationIsAlreadyExistException;
import net.safety.exception.FireStationNotFoundException;
import net.safety.model.FireStation;
import net.safety.repository.FireStationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FireStationService {

    private final FireStationRepository fireStationRepository;
    private final FireStationDtoConverter fireStationDtoConverter;
    private final static Logger logger = LoggerFactory.getLogger(FireStationService.class);

    public FireStationService(FireStationRepository fireStationRepository, FireStationDtoConverter fireStationDtoConverter) {
        this.fireStationRepository = fireStationRepository;
        this.fireStationDtoConverter = fireStationDtoConverter;
    }

    public Set<FireStationDto> getAllFireStationsDto(){
        try {
            return FireStationRepository.listFireStations.stream()
                    .map(fireStationDtoConverter::convert).collect(Collectors.toSet());
        }catch (DataLoadErrorException e){
            logger.error("Error when reading the data file... ");
            throw new DataLoadErrorException("Error when reading the data file... ");
        }
    }

    public FireStationDto saveFireStation(FireStation from) {
        try{
            return fireStationDtoConverter.convert(fireStationRepository.addForeStation(from));
        }catch (FireStationIsAlreadyExistException e){
            logger.error("Same FireStation exists, creation failed.");
            throw new FireStationIsAlreadyExistException("Same Firestation exists");
        }
    }

    public FireStationDto updateFireStation(FireStation from) {
        try {
            return fireStationDtoConverter.convert(fireStationRepository.updateFireStation(from));
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
            throw new FireStationNotFoundException("SERVICE This address and number specified doesn't correspond to a fire station");
        }
    }

    public List<FireStationDto> getFireStationByNumber(int stationNumber) {
        try {
            return fireStationRepository.getFireStationByNumber(stationNumber).stream()
                    .map(fireStationDtoConverter::convert).collect(Collectors.toList());
        }catch (FireStationNotFoundException e){
            logger.error("FireStation specified with this number : " + stationNumber + " doesn't exist.");
            throw new FireStationNotFoundException("FireStation specified with this number : " + stationNumber + " doesn't exist.");
        }
    }
}
