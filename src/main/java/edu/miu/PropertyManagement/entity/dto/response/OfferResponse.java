package edu.miu.PropertyManagement.entity.dto.response;

import edu.miu.PropertyManagement.entity.Offer;
import edu.miu.PropertyManagement.entity.Property;
import edu.miu.PropertyManagement.enums.OfferStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferResponse {
    private Long id;
    private Long userId;
    private String userName;
    private String userEmail;
    private OfferStatus status;
    private Double amount;
    private PropertyResponse property;


    public static OfferResponse fromOffer(Offer offer){
        OfferResponse response=new OfferResponse();
        response.setId(offer.getId());
        if (offer.getUser() != null) {
            response.setUserId(offer.getUser().getId());
            response.setUserName(offer.getUser().getName());
            response.setUserEmail(offer.getUser().getEmail());
        }
        response.setStatus(offer.getStatus());
        response.setAmount(offer.getAmount());
      response.setProperty(PropertyResponse.fromProperty(offer.getProperty()));
        return response;
    }
}
