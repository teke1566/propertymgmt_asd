package edu.miu.PropertyManagement.service.impl;

import edu.miu.PropertyManagement.entity.FavoriteList;
import edu.miu.PropertyManagement.entity.Property;
import edu.miu.PropertyManagement.entity.User;
import edu.miu.PropertyManagement.repository.FavoriteListRepository;
import edu.miu.PropertyManagement.repository.PropertyRepository;

import edu.miu.PropertyManagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class FavoriteListService {
    private  final FavoriteListRepository savedListRepository;
    private final PropertyRepository propertyRepository;
    private  final UserRepository userRepository;
    public FavoriteListService(FavoriteListRepository favoriteListRepository,
                               PropertyRepository propertyRepository,
                               UserRepository userRepository){
        this.savedListRepository= favoriteListRepository;
        this.propertyRepository=propertyRepository;
        this.userRepository=userRepository;
    }





    public void addToFavoriteList(Long customerId, Long propertyId,String name){
        Optional<FavoriteList> savedListOptional = savedListRepository.findByCustomerIdAndPropertiesId(customerId,propertyId);

        Property property = propertyRepository.findById(propertyId).get();
        User user= userRepository.findById(customerId).get();

        if(savedListOptional.isEmpty()){
            FavoriteList favoriteList =new FavoriteList();
            favoriteList.setName(name);
            favoriteList.setProperties(Arrays.asList(property));
            favoriteList.setCustomer(user);
            savedListRepository.save(favoriteList);
        }
        else {
            property.setFavoriteList(savedListOptional.get());
            propertyRepository.save(property);
        }
    }

    public List<FavoriteList> getFavoriteList(Long customerId){
        return savedListRepository.findByCustomerId(customerId);
    }
    public void removeFavoriteList(Long savedPropertyId){
        savedListRepository.deleteById(savedPropertyId);
    }

}
