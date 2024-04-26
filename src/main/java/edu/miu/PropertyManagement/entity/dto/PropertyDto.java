package edu.miu.PropertyManagement.entity.dto;

import edu.miu.PropertyManagement.entity.Property;
import lombok.Data;

@Data
public class PropertyDto {
    private Long id;
    private String propertyName;
    private String propertyType;
    private int numberOfRooms;
    private int numberOfBathRooms;
    private Double rentAmount;
    private Double securityDepositAmount;
    private AddressDto address;
    private String imageUrl;
    public static PropertyDto fromProperty(Property property){
        PropertyDto response=new PropertyDto();
        response.setId(property.getId());
        response.setPropertyName(property.getPropertyName());
        response.setPropertyType(property.getPropertyType());
        response.setNumberOfBathRooms(property.getNumberOfBathRooms());
        response.setImageUrl(property.getImageUrl());
        response.setNumberOfRooms(property.getNumberOfRooms());
        response.setRentAmount(property.getRentAmount());
        response.setAddress(AddressDto.fromAddress(property.getAddress()));
        return response;
    }

}
