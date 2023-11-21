package net.safety.response;

import net.safety.dto.ChildAndFamilyByAddressDto;
import net.safety.dto.PersonDto;
import net.safety.dto.PersonInfoDto;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChildAndFamilyByAddressResponse)) return false;
        ChildAndFamilyByAddressResponse that = (ChildAndFamilyByAddressResponse) o;
        return Objects.equals(childAndFamilyByAddressDto, that.childAndFamilyByAddressDto) && Objects.equals(listFamily, that.listFamily);
    }

    @Override
    public int hashCode() {
        return Objects.hash(childAndFamilyByAddressDto, listFamily);
    }
}
