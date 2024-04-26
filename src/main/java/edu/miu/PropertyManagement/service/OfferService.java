package edu.miu.PropertyManagement.service;

import com.itextpdf.text.DocumentException;
import edu.miu.PropertyManagement.entity.Offer;
import edu.miu.PropertyManagement.entity.dto.response.OfferResponse;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface OfferService {
    OfferResponse acceptOffer(Long offerId) throws ChangeSetPersister.NotFoundException;
    List<OfferResponse> findByCustomerId(Long customerId);
    OfferResponse rejectOffer(Long offerId) throws ChangeSetPersister.NotFoundException;
    List<OfferResponse> findOffersByPropertyId(Long propertyId);
    List<OfferResponse> findAllActiveOffers();
    List<OfferResponse> findActiveOffersByOwnerId(Long ownerId);
    List<OfferResponse> findActiveOffersByCustomerId(Long customerId);
    List<OfferResponse> findActiveOffersPropertiesForOwner(Long ownerId);
    void cancelOffer(Long offerId);
    boolean canCancelOffer(Long offerId);
    ByteArrayInputStream generateReceipt(Long offerId) throws DocumentException;


}
