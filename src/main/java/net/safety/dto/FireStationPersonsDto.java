package net.safety.dto;

import net.safety.model.Person;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class FireStationPersonsDto {

    private String address;
    private int stationNumber;

    private Set<Person> personList = new HashSet<>();

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(int stationNumber) {
        this.stationNumber = stationNumber;
    }

    public Set<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(Set<Person> personList) {
        this.personList = personList;
    }

    public FireStationPersonsDto() {

    }
    public FireStationPersonsDto(String address, int stationNumber, Set<Person> personList) {
        this.address = address;
        this.stationNumber = stationNumber;
        this.personList = personList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FireStationPersonsDto)) return false;
        FireStationPersonsDto that = (FireStationPersonsDto) o;
        return stationNumber == that.stationNumber && Objects.equals(address, that.address) && Objects.equals(personList, that.personList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, stationNumber, personList);
    }
}
