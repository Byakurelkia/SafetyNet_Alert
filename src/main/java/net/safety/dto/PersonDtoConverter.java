package net.safety.dto;

import net.safety.model.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonDtoConverter {

    public PersonDto convert(Person from){
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





}
