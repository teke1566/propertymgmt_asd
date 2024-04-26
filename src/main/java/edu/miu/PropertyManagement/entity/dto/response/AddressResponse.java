package edu.miu.PropertyManagement.entity.dto.response;

import edu.miu.PropertyManagement.entity.Address;
import edu.miu.PropertyManagement.entity.dto.AddressDto;
import lombok.Data;

@Data
public class AddressResponse {
    private Long id;

    private String street;

    private String city;

    private String zipcode;

    private String state;
    private  PropertyWithoutAddressResponse property;
    public static AddressResponse fromAddress (Address address) {
        AddressResponse addressDto = new AddressResponse();
        addressDto.setStreet(address.getStreet());
        addressDto.setCity(address.getCity());
        addressDto.setZipcode(address.getCity());
        addressDto.setState(address.getState());
        if(address.getProperty()!=null){
            addressDto.setProperty(PropertyWithoutAddressResponse.fromProperty(address.getProperty()));
        }
        return addressDto;
    }
}
