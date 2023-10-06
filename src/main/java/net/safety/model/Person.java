package net.safety.model;

import java.util.*;

public class Person {

    private String firstName;
    private String lastName;
    private String adress;
    private String city;
    private String mail;
    private String phoneNumber;
    private String zipCode;

    public void setMedicalRecords(Set<MedicalRecord> medicalRecords) {
        this.medicalRecords = medicalRecords;
    }

    public List<FireStation> getFireStation() {
        return fireStation;
    }

    public void addFireStation(FireStation fireStation) {
        this.fireStation.add(fireStation);
    }

    public Person() {
    }

    public Person(String firstName, String lastName, String adress, String city, String email, String phone, String zip) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.adress = adress;
        this.city = city;
        this.mail = email;
        this.phoneNumber = phone;
        this.zipCode =zip;
    }


    private Set<MedicalRecord> medicalRecords = new HashSet<>();
    private List<FireStation> fireStation = new ArrayList<>();



    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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

    public Set<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }

    public void addMedicalRecords(MedicalRecord medicalRecords) {
        this.medicalRecords.add(medicalRecords);
    }



    public void deleteFireStation(){
        this.fireStation = null;
    }

    /*@Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", adress='" + adress + '\'' +
                ", city='" + city + '\'' +
                ", mail='" + mail + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", medicalRecords=" + medicalRecords +
                ", fireStation=" + fireStation +
                '}';
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(adress, person.adress) && Objects.equals(city, person.city) && Objects.equals(mail, person.mail) && Objects.equals(phoneNumber, person.phoneNumber) && Objects.equals(zipCode, person.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, adress, city, mail, phoneNumber, zipCode);
    }
}
