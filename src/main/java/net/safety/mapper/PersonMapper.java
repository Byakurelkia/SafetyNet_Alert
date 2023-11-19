package net.safety.mapper;

import net.safety.dto.PersonDto;
import net.safety.dto.PersonInfoByLastNameDto;
import net.safety.dto.PersonInfoDto;
import net.safety.dto.PersonsInfoByAddressOrStationNumberDto;
import net.safety.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    Person personDtoToPerson(PersonDto personDto);

    PersonInfoDto personToPersonInfoDto(Person person);

    PersonsInfoByAddressOrStationNumberDto personToPersonByAddressDto(Person person);

    PersonInfoByLastNameDto personToPersonInfoByLastNameDto(Person person);
}
