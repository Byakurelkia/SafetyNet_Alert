package net.safety.response;

import net.safety.dto.PersonsInfoByAddressOrStationNumberDto;

import java.util.List;

public class PersonsByAddressResponse {

    List<PersonsInfoByAddressOrStationNumberDto> personsInfoByAddressOrStationNumberDtoList;
    List<Integer> stationNumber;

    public PersonsByAddressResponse() {
    }

    public PersonsByAddressResponse(List<PersonsInfoByAddressOrStationNumberDto> personsInfoByAddressOrStationNumberDtoList, List<Integer> stationNumber) {
        this.personsInfoByAddressOrStationNumberDtoList = personsInfoByAddressOrStationNumberDtoList;
        this.stationNumber = stationNumber;
    }

    public List<PersonsInfoByAddressOrStationNumberDto> getPersonsByAddressDtoList() {
        return personsInfoByAddressOrStationNumberDtoList;
    }

    public void setPersonsByAddressDtoList(List<PersonsInfoByAddressOrStationNumberDto> personsInfoByAddressOrStationNumberDtoList) {
        this.personsInfoByAddressOrStationNumberDtoList = personsInfoByAddressOrStationNumberDtoList;
    }

    public List<Integer> getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(List<Integer> stationNumber) {
        this.stationNumber = stationNumber;
    }

    @Override
    public String toString() {
        return "PersonsByAddressResponse{" +
                "personsInfoByAddressOrStationNumberDtoList=" + personsInfoByAddressOrStationNumberDtoList +
                ", stationNumber=" + stationNumber +
                '}';
    }
}

