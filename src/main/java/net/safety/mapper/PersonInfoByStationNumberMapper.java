package net.safety.mapper;

import net.safety.dto.PersonInfoByStationNumberDto;
import net.safety.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonInfoByStationNumberMapper {

    PersonInfoByStationNumberMapper INSTANCE = Mappers.getMapper(PersonInfoByStationNumberMapper.class);

    PersonInfoByStationNumberDto personInfoByStationNumberDto(Person person);
}
