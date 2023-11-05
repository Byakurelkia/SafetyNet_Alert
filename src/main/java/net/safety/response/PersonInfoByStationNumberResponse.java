package net.safety.response;

import net.safety.dto.PersonInfoByStationNumberDto;

import java.util.List;
import java.util.Set;

public class PersonInfoByStationNumberResponse {

    List<PersonInfoByStationNumberDto> listPersonInfoByStationNumberDto;
    Integer nmbAdult;
    Integer nmbChild;

    public PersonInfoByStationNumberResponse() {
    }

    public PersonInfoByStationNumberResponse(List<PersonInfoByStationNumberDto> listPersonInfoByStationNumberDto, Integer nmbAdult, Integer nmbChild) {
        this.listPersonInfoByStationNumberDto = listPersonInfoByStationNumberDto;
        this.nmbAdult = nmbAdult;
        this.nmbChild = nmbChild;
    }

    public List<PersonInfoByStationNumberDto> getListPersonInfoByStationNumberDto() {
        return listPersonInfoByStationNumberDto;
    }

    public void setListPersonInfoByStationNumberDto(List<PersonInfoByStationNumberDto> listPersonInfoByStationNumberDto) {
        this.listPersonInfoByStationNumberDto = listPersonInfoByStationNumberDto;
    }

    public Integer getNmbAdult() {
        return nmbAdult;
    }

    public void setNmbAdult(Integer nmbAdult) {
        this.nmbAdult = nmbAdult;
    }

    public Integer getNmbChild() {
        return nmbChild;
    }

    public void setNmbChild(Integer nmbChild) {
        this.nmbChild = nmbChild;
    }

    @Override
    public String toString() {
        return
                "listPerson=" + listPersonInfoByStationNumberDto +
                ", nmbAdult=" + nmbAdult +
                ", nmbChild=" + nmbChild ;
    }
}
