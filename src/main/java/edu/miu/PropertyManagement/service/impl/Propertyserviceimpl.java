package edu.miu.PropertyManagement.service.impl;

import edu.miu.PropertyManagement.entity.Property;
import edu.miu.PropertyManagement.entity.Status;
import edu.miu.PropertyManagement.entity.User;
import edu.miu.PropertyManagement.entity.dto.PropertyDto;
import edu.miu.PropertyManagement.entity.dto.PropertySearchDao;
import edu.miu.PropertyManagement.entity.dto.SearchCriteria;
import edu.miu.PropertyManagement.entity.dto.response.PropertyResponse;
import edu.miu.PropertyManagement.enums.Roles;
import edu.miu.PropertyManagement.repository.PropertyRepository;
import edu.miu.PropertyManagement.repository.UserRepository;
import edu.miu.PropertyManagement.service.PropertyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class Propertyserviceimpl implements PropertyService {
    @Autowired
    PropertySearchDao propertySearchDao;

    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private ModelMapper modelmapper;

    @Autowired
    UserRepository userRepository;

    @Override
    public PropertyResponse post(Property property) {
        try {
            Status status = Status.AVAILABLE;
            property.setStatus(status);
            Property savedProperty = propertyRepository.save(property);
            return PropertyResponse.fromProperty(savedProperty);
        } catch (Exception e) {

            System.out.println("Error occurred while saving property: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<PropertyResponse> getAllProperty() {
        List<PropertyResponse> propertyDtos = new ArrayList<>();
        for (Property property : propertyRepository.findAll()) {
            var dto = modelmapper.map(property, PropertyResponse.class);
            propertyDtos.add(dto);
        }
        return propertyDtos;
    }

    @Override
    public PropertyResponse getPropertyById(Long id) {

        Property property = propertyRepository.findById(id).get();
        return modelmapper.map(property, PropertyResponse.class);
    }

    @Override
    public List<Property> propertiesByAddress(String city) {
        List<Property> properties = new ArrayList<>();
        for (Property p : propertyRepository.propertiesbyaddress(city)) {
            properties.add(p);
        }
        return properties;
    }
    public List<Property> propertiesByUserId(Long id){
        User user= userRepository.findById(id).get();

        if (Objects.equals(user.getRole(), Roles.OWNER)) {
            return propertyRepository.findAllById(id);
        }
        return null;
    }


    public void cancelContingency(Long id) {
        updateToAvailable(id);
    }

    @Override
    public PropertyResponse updateToPending(Long id) {
        Property property = propertyRepository.findById(id).orElse(null);
        if (property != null) {
            property.setStatus(Status.PENDING);
            propertyRepository.save(property);
            return PropertyResponse.fromProperty(property);
        }
        return null;
    }


    @Override
    public PropertyResponse updateToAvailable(Long id) {
        Property property = propertyRepository.findById(id).orElse(null);
        if (property != null) {
            property.setStatus(Status.AVAILABLE);
            propertyRepository.save(property);
            return PropertyResponse.fromProperty(property);
        }
        return null;
    }
    @Override
    public PropertyResponse updateToCONTINGENT(Long id) {
        Property property = propertyRepository.findById(id).orElse(null);
        if(property!=null) {
            property.setStatus(Status.CONTINGENT);
            propertyRepository.save(property);
            return PropertyResponse.fromProperty(property);
        }
        return null;
    }

    @Override
    public void deleteProperty(Long id) {
        Property property = propertyRepository.findById(id).get();
        Status status = Status.PENDING;
        if (!property.getStatus().equals(status)) {
            propertyRepository.deleteById(id);
        }
        System.out.println("property is pending can not be deleted");
    }


    @Override
    public Property updateProperty(Property property, Long userId) {
        Property toUpate = propertyRepository.findById(userId).orElse(null);
        if (toUpate != null) {
            toUpate.setPropertyType(property.getPropertyType());
            toUpate.setPropertyName(property.getPropertyName());
            toUpate.setDescription(property.getDescription());
            toUpate.setNumberOfBathRooms(property.getNumberOfBathRooms());
            toUpate.setAddress(property.getAddress());
            toUpate.setPrice(property.getPrice());
            toUpate.setNumberOfRooms(property.getNumberOfRooms());
            toUpate.setPostedDate(property.getPostedDate());
            toUpate.setRentAmount(property.getRentAmount());
            toUpate.setPostedDate(property.getPostedDate());

            //toUpate.setUser(property.getUser());
            toUpate.setSecurityDepositAmount(property.getSecurityDepositAmount());
            toUpate.setImageUrl(property.getImageUrl());
            propertyRepository.save(toUpate);
        }
        return toUpate;
    }

    @Override
    public List<Property> findByAllCriteria(String propertyType, String city, String status, Double price, Integer numberOfRooms) {
        var dtoSearchRequest=new SearchCriteria();
        dtoSearchRequest.setPropertyType(propertyType);
        dtoSearchRequest.setCity(city);
        dtoSearchRequest.setStatus(status);
        dtoSearchRequest.setPrice(price);
        dtoSearchRequest.setNumberOfRooms(numberOfRooms);
        return  propertySearchDao.findAllByCriteria(dtoSearchRequest);
    }

}
