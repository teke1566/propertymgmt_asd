package edu.miu.PropertyManagement.entity.dto.response;

import edu.miu.PropertyManagement.entity.Address;
import edu.miu.PropertyManagement.entity.Property;
import edu.miu.PropertyManagement.entity.dto.AddressDto;
import edu.miu.PropertyManagement.entity.dto.PropertyDto;
import lombok.Data;

@Data
public class AddressResponseWithoutProperty {

    private Long id;

    private String street;

    private String city;

    private String zipcode;

    private String state;
    public static AddressResponseWithoutProperty fromAddress (Address address) {
        AddressResponseWithoutProperty addressDto = new AddressResponseWithoutProperty();
        addressDto.setId(addressDto.getId());
        addressDto.setStreet(address.getStreet());
        addressDto.setCity(address.getCity());
        addressDto.setZipcode(address.getCity());
        addressDto.setState(address.getState());
        return addressDto;
    }
}
