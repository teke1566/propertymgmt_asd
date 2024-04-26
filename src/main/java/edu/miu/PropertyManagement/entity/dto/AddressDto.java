package edu.miu.PropertyManagement.entity.dto;

import edu.miu.PropertyManagement.entity.Address;
import lombok.Data;

@Data
public class AddressDto {

    private Long id;

    private String street;

    private String city;

    private String zipcode;

    private String state;
    public static AddressDto fromAddress (Address address) {
        AddressDto addressDto = new AddressDto();
        addressDto.setStreet(address.getStreet());
        addressDto.setCity(address.getCity());
        addressDto.setZipcode(address.getCity());
        addressDto.setState(address.getState());
        return addressDto;
    }

}
