package net.safety.mapper;

import net.safety.dto.FireStationPersonsDto;
import net.safety.model.FireStation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface FireStationMapper {

    FireStationMapper INSTANCE = Mappers.getMapper( FireStationMapper.class );

    FireStationPersonsDto fireStationToFireStationPersonsDto(FireStation fireStation);

}
