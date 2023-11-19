package net.safety.response;

import net.safety.dto.PersonsInfoByAddressOrStationNumberDto;

import java.util.List;
import java.util.Objects;

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

    @Override
    public String toString() {
        return "PersonsInfoByStationNumberListResponse{" +
                "address='" + address + '\'' +
                ", personsInfoByAddressOrStationNumberDtoList=" + personsInfoByAddressOrStationNumberDtoList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonsInfoByStationNumberListResponse)) return false;
        PersonsInfoByStationNumberListResponse that = (PersonsInfoByStationNumberListResponse) o;
        return Objects.equals(address, that.address) && Objects.equals(personsInfoByAddressOrStationNumberDtoList, that.personsInfoByAddressOrStationNumberDtoList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, personsInfoByAddressOrStationNumberDtoList);
    }
}
