package net.safety.dto;

import net.safety.model.FireStation;
import org.springframework.stereotype.Component;

@Component
public class FireStationDtoConverter {

    public FireStationDto convert(FireStation from){
        return new FireStationDto(
                from.getAddress(),
                from.getStationNumber()
        );
    }


}
