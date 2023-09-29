package net.safety.dto;

public class FireStationDto {

    private String address;
    private int stationNumber;

    public FireStationDto(String address, int stationNumber) {
        this.address=address;
        this.stationNumber = stationNumber;
    }

    @Override
    public String toString() {
        return "address='" + address + '\'' +
                ", stationNumber=" + stationNumber +
                '}';
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
}
