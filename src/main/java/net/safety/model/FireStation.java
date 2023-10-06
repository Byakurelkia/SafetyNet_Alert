package net.safety.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class FireStation {

    private String address ;
    private int stationNumber;

    public FireStation() {
    }

    public FireStation(String address, int station) {
        this.address=address;
        this.stationNumber = station;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address=address;
    }

    public int getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(int stationNumber) {
        this.stationNumber = stationNumber;
    }


    /*@Override
    public String toString() {
        return "{Adress = " + address.toString() + " - " + "stationNumber =" + stationNumber +
                '}';
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FireStation)) return false;
        FireStation that = (FireStation) o;
        return stationNumber == that.stationNumber && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, stationNumber);
    }
}
