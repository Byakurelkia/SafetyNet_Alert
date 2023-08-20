package net.safety.dto;

import net.safety.model.Person;

public class PersonDto {

    private String firstName;
    private String lastName;
    private String adress;
    private String city;
    private String mail;
    private String phoneNumber;
    private String zipCode;


    public PersonDto(Person from){
        this.firstName = from.getFirstName();
        this.lastName = from.getLastName();
        this.adress = from.getAdress();
        this.city = from.getCity();
        this.mail = from.getMail();
        this.phoneNumber= from.getPhoneNumber();
        this.zipCode = from.getZipCode();
    }

    public PersonDto(String firstName, String lastName, String mail, String phoneNumber, String adress, String zipCode, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.phoneNumber =phoneNumber;
        this.adress = adress;
        this.zipCode = zipCode;
        this.city = city;
    }

    @Override
    public String toString() {
        return "PersonDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", adress='" + adress + '\'' +
                ", city='" + city + '\'' +
                ", mail='" + mail + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
