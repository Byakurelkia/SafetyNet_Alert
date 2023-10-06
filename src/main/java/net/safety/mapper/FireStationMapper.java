package net.safety.mapper;

import net.safety.dto.FireStationPersonsDto;
import net.safety.model.FireStation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring")
public interface FireStationMapper {
    FireStationMapper INSTANCE = Mappers.getMapper( FireStationMapper.class );

   // @Mapping(source = "numberOfSeats", target = "seatCount")
    FireStationPersonsDto fireStationToFireStationPersonsDto(FireStation fireStation);
/*
    @Override
    public List<FireStationPersonsDto> fireStationsToFireStationPersonsDtos(List<FireStation> fireStationList) {
        return fireStationList.stream().map(this::fireStationToFireStationPersonsDto).collect(Collectors.toList());
    }*/
}
