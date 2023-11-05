package net.safety.dto;

public class PersonDto {

    private String address;
    private String city;
    private String mail;
    private String phoneNumber;
    private String zipCode;

    public PersonDto(String mail, String phoneNumber, String address, String zipCode, String city) {
        this.mail = mail;
        this.phoneNumber =phoneNumber;
        this.address = address;
        this.zipCode = zipCode;
        this.city = city;
    }

    public PersonDto() {}

    @Override
    public String toString() {
        return "PersonDto{" +
                ", adress='" + address + '\'' +
                ", city='" + city + '\'' +
                ", mail='" + mail + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
