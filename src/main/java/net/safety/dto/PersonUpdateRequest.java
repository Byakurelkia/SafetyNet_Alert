package net.safety.dto;

public class PersonUpdateRequest {
    private String adress;
    private String city;
    private String mail;
    private String phoneNumber;
    private String zipCode;

    public PersonUpdateRequest(){
    }

    public PersonUpdateRequest(String adress, String city, String mail, String phoneNumber, String zipCode) {
        this.adress = adress;
        this.city = city;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
        this.zipCode = zipCode;
    }

    public String getAdress() {
        return adress;
    }

    public String getCity() {
        return city;
    }

    public String getMail() {
        return mail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getZipCode() {
        return zipCode;
    }


}
