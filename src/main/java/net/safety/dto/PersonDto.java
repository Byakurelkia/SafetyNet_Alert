package net.safety.dto;

public class PersonDto {

    private String firstName;
    private String lastName;
    private String adress;
    private String city;
    private String mail;
    private String phoneNumber;
    private String zipCode;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
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
