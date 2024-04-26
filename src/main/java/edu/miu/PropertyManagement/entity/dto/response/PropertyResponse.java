package edu.miu.PropertyManagement.entity.dto.response;

import edu.miu.PropertyManagement.entity.Property;
import edu.miu.PropertyManagement.entity.Status;
import edu.miu.PropertyManagement.entity.dto.AddressDto;
import lombok.Data;

@Data
public class PropertyResponse {
    private Long id;
    private String propertyName;
    private String propertyType;
    private int numberOfRooms;
    private int numberOfBathRooms;
    private Double rentAmount;
    private Double securityDepositAmount;
    private AddressResponseWithoutProperty address;
    private Status status;
    private String imageUrl;
    public static PropertyResponse fromProperty(Property property){
        PropertyResponse response=new PropertyResponse();
        response.setId(property.getId());
        response.setPropertyName(property.getPropertyName());
        response.setPropertyType(property.getPropertyType());
        response.setNumberOfBathRooms(property.getNumberOfBathRooms());
        response.setImageUrl(property.getImageUrl());
        response.setNumberOfRooms(property.getNumberOfRooms());
        response.setRentAmount(property.getRentAmount());
        response.setSecurityDepositAmount(property.getSecurityDepositAmount());
        response.setStatus(property.getStatus());
        if(property.getAddress()!=null){
            response.setAddress(AddressResponseWithoutProperty.fromAddress(property.getAddress()));
        }
        return response;
    }

}

