package net.safety.dto;

import net.safety.model.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonDtoConverter {

    public PersonDto convertToDto(Person from){
        return new PersonDto(
                from.getFirstName(),
                from.getLastName(),
                from.getMail(),
                from.getPhoneNumber(),
                from.getAdress(),
                from.getZipCode(),
                from.getCity()
        );
    }

    public Person convertToPerson(PersonCreateRequest from){
        return new Person(
                from.getFirstName(),
                from.getLastName(),
                from.getMail(),
                from.getPhoneNumber(),
                from.getAdress(),
                from.getZipCode(),
                from.getCity()
        );
    }





}
