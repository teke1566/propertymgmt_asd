package edu.miu.PropertyManagement.service;

import edu.miu.PropertyManagement.entity.User;
import edu.miu.PropertyManagement.entity.dto.response.OfferResponse;

import java.util.List;

public interface CustomerService {
    OfferResponse placeOffer(Long propertyId, Long userId, Double amount);
    List<User> getAllCustomers();
}
