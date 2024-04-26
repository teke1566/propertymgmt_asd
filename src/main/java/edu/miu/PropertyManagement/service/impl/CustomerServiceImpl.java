package edu.miu.PropertyManagement.service.impl;

import edu.miu.PropertyManagement.entity.Offer;
import edu.miu.PropertyManagement.entity.Property;
import edu.miu.PropertyManagement.entity.Status;
import edu.miu.PropertyManagement.entity.User;
import edu.miu.PropertyManagement.entity.dto.response.OfferResponse;
import edu.miu.PropertyManagement.enums.OfferStatus;
import edu.miu.PropertyManagement.repository.FavoriteListRepository;
import edu.miu.PropertyManagement.repository.OfferRepository;
import edu.miu.PropertyManagement.repository.PropertyRepository;
import edu.miu.PropertyManagement.repository.UserRepository;
import edu.miu.PropertyManagement.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CustomerServiceImpl implements CustomerService {
@Autowired
    private OfferRepository offerRepository;
@Autowired
    private FavoriteListRepository favoriteListRepository;
@Autowired
    private PropertyRepository propertyRepository;
@Autowired
    private UserRepository userRepository;
@Autowired
    private  EmailService emailService;



    public List<User> getAllCustomers() {
        return userRepository.findAllCustomers();
    }


 /*
 *Place offer, the property status will be changed to 'pending'
 *  if the offer gets accepted:
When a customer places an offer, update the
*  property status to 'pending' if the offer is accepted.
*  You can implement this logic in the CustomerService.
 * */

    public OfferResponse placeOffer(Long propertyId, Long userId, Double amount) {
        Property property = propertyRepository.findById(propertyId).orElse(null);
        if (property == null) {
            return null;
        }

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }

        Offer offer = new Offer();
        offer.setProperty(property);
        offer.setUser(user);
        offer.setAmount(amount);
        offer.setStatus(OfferStatus.PENDING);

        Offer savedOffer = offerRepository.save(offer);

        if (savedOffer != null) {
            property.setStatus(Status.PENDING);
            propertyRepository.save(property);
        }

        String ownerEmail = savedOffer.getProperty().getUsers_id().getEmail();
        //String ownerEmail1 ="getaunayaleneh@gmail.com";
        String subject = "New offer on your property";
        String message = "You have received a new offer of " + amount + " on your property at " +
                savedOffer.getProperty().getAddress().getCity();
        emailService.sendEmail(ownerEmail, subject, message);

        return OfferResponse.fromOffer(savedOffer);
    }
}


