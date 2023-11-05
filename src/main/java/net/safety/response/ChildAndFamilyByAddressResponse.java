package net.safety.response;

import net.safety.dto.ChildAndFamilyByAddressDto;
import net.safety.dto.PersonDto;
import net.safety.dto.PersonInfoDto;

import java.util.List;

public class ChildAndFamilyByAddressResponse {

    List<ChildAndFamilyByAddressDto> childAndFamilyByAddressDto;
    List<PersonInfoDto> listFamily;

    public ChildAndFamilyByAddressResponse() {
    }

    public ChildAndFamilyByAddressResponse(List<ChildAndFamilyByAddressDto> childAndFamilyByAddressDto,List<PersonInfoDto> listFamily) {
        this.childAndFamilyByAddressDto = childAndFamilyByAddressDto;
        this.listFamily = listFamily;
    }

    public List<ChildAndFamilyByAddressDto> getChildAndFamilyByAddressDto() {
        return childAndFamilyByAddressDto;
    }

    public void setChildAndFamilyByAddressDto(List<ChildAndFamilyByAddressDto> childAndFamilyByAddressDto) {
        this.childAndFamilyByAddressDto = childAndFamilyByAddressDto;
    }

    public List<PersonInfoDto> getListFamily() {
        return listFamily;
    }

    public void setListFamily(List<PersonInfoDto> listFamily) {
        this.listFamily = listFamily;
    }

    @Override
    public String toString() {
        return "ChildAndFamilyByAddressResponse{" +
                "childInfo=" + childAndFamilyByAddressDto +
                ", listFamily=" + listFamily +
                '}';
    }
}
