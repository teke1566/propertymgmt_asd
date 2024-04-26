package edu.miu.PropertyManagement.entity.dto.response;

import edu.miu.PropertyManagement.entity.Offer;
import edu.miu.PropertyManagement.enums.OfferStatus;
import lombok.Data;

@Data
public class OfferResponseWithProperty {
    private Long id;
//    private Long userId;
//    private String userName;
//    private String userEmail;
    private OfferStatus status;
    private Double amount;
    private PropertyResponse property;


    public static OfferResponseWithProperty fromOffer(Offer offer){
        OfferResponseWithProperty response=new OfferResponseWithProperty();
        response.setId(offer.getId());
//        if (offer.getUser() != null) {
//            response.setUserId(offer.getUser().getId());
//            response.setUserName(offer.getUser().getName());
//            response.setUserEmail(offer.getUser().getEmail());
//        }
        response.setStatus(offer.getStatus());
        response.setAmount(offer.getAmount());
       response.setProperty(PropertyResponse.fromProperty(offer.getProperty()));
        return response;
    }
}
