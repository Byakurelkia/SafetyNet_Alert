package net.safety.mapper;

import net.safety.dto.ChildAndFamilyByAddressDto;
import net.safety.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChildAndFamilyByAddressMapper {

    ChildAndFamilyByAddressMapper INSTANCE = Mappers.getMapper(ChildAndFamilyByAddressMapper.class);

    ChildAndFamilyByAddressDto childAndFamilyByAddressDto(Person person);
}
