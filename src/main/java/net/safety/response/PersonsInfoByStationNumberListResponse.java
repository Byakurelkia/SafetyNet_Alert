package net.safety.response;

import net.safety.dto.PersonsInfoByAddressOrStationNumberDto;

import java.util.List;

public class PersonsInfoByStationNumberListResponse {

    String address;
    List<PersonsInfoByAddressOrStationNumberDto> personsInfoByAddressOrStationNumberDtoList;

    public PersonsInfoByStationNumberListResponse() {
    }

    public PersonsInfoByStationNumberListResponse(String address, List<PersonsInfoByAddressOrStationNumberDto> personsInfoByAddressOrStationNumberDtoList) {
        this.address = address;
        this.personsInfoByAddressOrStationNumberDtoList = personsInfoByAddressOrStationNumberDtoList;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<PersonsInfoByAddressOrStationNumberDto> getPersonsInfoByAddressOrStationNumberDtoList() {
        return personsInfoByAddressOrStationNumberDtoList;
    }

    public void setPersonsInfoByAddressOrStationNumberDtoList(List<PersonsInfoByAddressOrStationNumberDto> personsInfoByAddressOrStationNumberDtoList) {
        this.personsInfoByAddressOrStationNumberDtoList = personsInfoByAddressOrStationNumberDtoList;
    }
}
