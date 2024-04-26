package edu.miu.PropertyManagement.service;


import edu.miu.PropertyManagement.entity.Property;
import edu.miu.PropertyManagement.entity.dto.PropertyDto;
import edu.miu.PropertyManagement.entity.dto.response.PropertyResponse;

import java.util.List;


public interface PropertyService {
    PropertyResponse post(Property property);
    List<PropertyResponse> getAllProperty();
    PropertyResponse getPropertyById(Long id);
    List<Property> propertiesByAddress(String city);
    public List<Property> propertiesByUserId(Long id);

    void cancelContingency(Long id);

    PropertyResponse updateToPending(Long id);

    PropertyResponse updateToAvailable(Long id);

    PropertyResponse updateToCONTINGENT(Long id);

    void deleteProperty(Long id);

    Property updateProperty(Property property, Long userId);
    public List<Property>findByAllCriteria(String propertyType,String city,String status,Double price, Integer numberOfRooms );





}
