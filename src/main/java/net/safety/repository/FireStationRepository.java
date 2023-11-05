package net.safety.repository;

import com.jsoniter.any.Any;
import net.safety.dataLoad.DataLoadInit;
import net.safety.dto.FireStationPersonsDto;
import net.safety.exception.AddressNotFoundException;
import net.safety.exception.DataLoadErrorException;
import net.safety.exception.FireStationIsAlreadyExistException;
import net.safety.exception.FireStationNotFoundException;
import net.safety.model.FireStation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class FireStationRepository{

    public static Set<FireStation> listFireStations = new HashSet<>();
    private final DataLoadInit dataLoadInit;
    private final Logger logger = LoggerFactory.getLogger(FireStationRepository.class);


    public FireStationRepository(DataLoadInit dataLoadInit) {
        this.dataLoadInit = dataLoadInit;
        getAllFireStationsFromFile();
    }

    private void getAllFireStationsFromFile() {
        logger.info("Reading Data From JSON File Started");
        try {
            Any allFireStationsFromJSON = dataLoadInit.readerFileJSON().get("firestations");
            allFireStationsFromJSON.forEach(f->
                    {
                        listFireStations.add(new FireStation(
                                        f.get("address").toString(),
                                        f.get("station").toInt()
                                )
                        );
                    }
            );
        }catch (DataLoadErrorException e){
            logger.error("Error when reading data from file..");
            e.getMessage();
            throw new DataLoadErrorException("Error when reading data from file..");
        }
        logger.info("Fire station data reading Completed successfully");
    }

    public Set<FireStation> getAllFireStations(){
        try{
            return listFireStations;
        }catch (DataLoadErrorException e){
            logger.error("Error when reading fire stations data from file !");
            throw new DataLoadErrorException("Error when reading fire stations data from file !");
        }
    }

    public List<FireStation> getFireStationByNumber(int number){
        logger.info("getFireStationByNumber started..");
        List<FireStation> station =
        listFireStations.stream()
                .filter(f-> f.getStationNumber() == number)
                .collect(Collectors.toList());

        if (station.size() == 0){
            logger.error("FireStation specified with this number : " + number + " doesn't exist");
            throw new FireStationNotFoundException("FireStation specified with this number : " + number + " doesn't exist.");
        }
        logger.info("getFireStationByNumber realised successfully.");
        return  station;
    }

    public Optional<List<FireStation>> getFireStationByAddress(String address){
        logger.info("getFireStationByAddress started..");
        List<FireStation> station =
                listFireStations.stream()
                        .filter(f-> f.getAddress().equals(address))
                        .collect(Collectors.toList());

        if (station.size() == 0){
            logger.error("FireStation specified with this address : '" + address + "' doesn't exist");
            throw new FireStationNotFoundException("FireStation specified with this address : '" + address + "' doesn't exist.");
        }

        logger.info("getFireStationByAddress realised successfully.");
        return  Optional.ofNullable(station);
    }

    public Optional<FireStation> getFireStationByAddressAndNumber(FireStation from){
        logger.info("getFireStationByAddressAndNumber started..");

        FireStation fireStation = new FireStation();
        for (FireStation f : listFireStations){
            if (f.getStationNumber() == from.getStationNumber() && f.getAddress().equals(from.getAddress()))
            {
                fireStation = f;
                break;
            }
            else
                fireStation = null;
        }

        if (fireStation == null){
            logger.error("FireStation with couple address : '" + from.getAddress() + "' and number : '" + from.getStationNumber() + "' doesn't exist!");
            throw new FireStationNotFoundException("FireStation with couple address : '" + from.getAddress() + "' and number : '" + from.getStationNumber() + "' doesn't exist!");
        }

        logger.info("getFireStationByAddressAndNumber realised successfully.");
        return Optional.ofNullable(fireStation);
    }

    public FireStation addForeStation(FireStation from) {
        logger.info("addFireStation started..");
        boolean result = isFireStationExist(from);
        if (result){
            logger.error("Same FireStation exists, creation failed.");
            throw new FireStationIsAlreadyExistException("Same Firestation exists");
        }

        listFireStations.add(from);
        logger.info("The Firestation is created successfully.");
        return from;

    }

    public FireStation updateFireStation(FireStation from){
        logger.info("updateFireStation is started..");
        boolean result = isAdressFireStationExist(from);
        Iterator<FireStation> iterator= listFireStations.iterator();

        if(!result) {
            logger.error("FireStation with specified address doesnt exist! update is failed.");
            throw new AddressNotFoundException("FireStation with specified address doesnt exist! update is failed.");
        }

        while (iterator.hasNext()) {
            FireStation f = iterator.next();
            if (f.getAddress().equals(from.getAddress())) {
                f.setStationNumber(from.getStationNumber());
                logger.info("FireStation is successfully updated ");
                break;
            }
        }
        return from;

    }

    public void deleteFireStationForAnAddress(String address, int number) {
        logger.info("deleteFireStationForAnAddress is started..");
        Iterator<FireStation> iterator = listFireStations.iterator();
        boolean result = false;

        while (iterator.hasNext()) {
            FireStation f = iterator.next();
            if (f.getAddress().equals(address) && f.getStationNumber() == number) {
                iterator.remove();
                logger.info("FireStation is successfully deleted ");
                result = true;
                break;
            }
        }
        if (!result){
            logger.error("This address and number specified doesnt correspond to a firestation, delete failed.");
            throw new FireStationNotFoundException("This address and number specified doesn't correspond to a fire station");
        }
    }
/*
    public Set<FireStationPersonsDto> fireStationWithPersonList(){
        Set<FireStationPersonsDto> fireStationsWithPersonList = listFireStations;
        fireStationsWithPersonList.forEach(f->{
            PersonRepository.listPersons.forEach(p->{
                if (p.getAdress().equals(f.getAddress()) && !fireStationsWithPersonList.contains(p))
                    f.addPerson(p);
            });
        });

        return fireStationsWithPersonList;
    }*/


    private boolean isAdressFireStationExist(FireStation fireStation) {
        boolean result = false;

        for (FireStation f : listFireStations){
            if (f.getAddress().equals(fireStation.getAddress())){
                result = true;
                break;
            }
        }

        return result;
    }

    private boolean isFireStationExist(FireStation fireStation){
        boolean result = listFireStations.contains(fireStation);
        return result;
    }

}
