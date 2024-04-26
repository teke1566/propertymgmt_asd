package edu.miu.PropertyManagement.entity.dto.response;

import edu.miu.PropertyManagement.entity.Property;
import edu.miu.PropertyManagement.entity.dto.AddressDto;
import edu.miu.PropertyManagement.entity.dto.PropertyDto;
import lombok.Data;

@Data
public class PropertyWithoutAddressResponse {
    private Long id;
    private String propertyName;
    private String propertyType;
    private int numberOfRooms;
    private int numberOfBathRooms;
    private Double rentAmount;
    private Double securityDepositAmount;
    private String imageUrl;
    public static PropertyWithoutAddressResponse fromProperty(Property property){
        PropertyWithoutAddressResponse response=new PropertyWithoutAddressResponse();
        response.setId(property.getId());
        response.setPropertyName(property.getPropertyName());
        response.setPropertyType(property.getPropertyType());
        response.setNumberOfBathRooms(property.getNumberOfBathRooms());
        response.setImageUrl(property.getImageUrl());
        response.setNumberOfRooms(property.getNumberOfRooms());
        response.setRentAmount(property.getRentAmount());
        return response;
    }
}
