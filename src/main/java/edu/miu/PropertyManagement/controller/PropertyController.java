package edu.miu.PropertyManagement.controller;

import edu.miu.PropertyManagement.entity.Property;
import edu.miu.PropertyManagement.entity.dto.response.OfferResponse;
import edu.miu.PropertyManagement.entity.dto.response.PropertyResponse;
import edu.miu.PropertyManagement.enums.Roles;
import edu.miu.PropertyManagement.service.PropertyService;
import edu.miu.PropertyManagement.service.impl.OfferServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/properties")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PropertyController {
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private OfferServiceImpl offerServiceImpl;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<PropertyResponse> getAllProperties() {
        return propertyService.getAllProperty();
    }

    @PostMapping("/owner")
    @ResponseStatus(HttpStatus.CREATED)
    public PropertyResponse postProperty(@RequestBody @Valid Property property) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority(Roles.OWNER.name()))) {
            throw new AccessDeniedException("You are not authorized to access this resource.");
        }
       return  propertyService.post(property);
    }

    @GetMapping("/{id}")
    public PropertyResponse getPropertyById(@PathVariable Long id) {
        return propertyService.getPropertyById(id);
    }

    @PutMapping("/owner/{id}")
    public Property updateProperty(@RequestBody Property property, @PathVariable Long id) {
        return propertyService.updateProperty(property, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/owner/{id}")
    public void deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
    }

    @PutMapping("/owner/{id}/pend")
    public PropertyResponse UpdateToPending(@PathVariable Long id) {
        return propertyService.updateToPending(id);
    }

    @PutMapping("/owner/{id}/available")
    public PropertyResponse UpdateToAvailable(@PathVariable Long id) {
       return propertyService.updateToAvailable(id);
    }

    @PutMapping("/owner/{id}/contigent")
    public PropertyResponse UpdateToCONTINGENT(@PathVariable Long id) {
        return propertyService.updateToCONTINGENT(id);
    }

    @GetMapping("/city/{city}")
    public List<Property> getPropertyBycity(@PathVariable String city) {
        return propertyService.propertiesByAddress(city);
    }


    @GetMapping("/criteria")
    public List<Property> getPropertyByCriteria(@RequestParam(value = "propertyType", required = false) String propertyType,
                                                @RequestParam(value = "city", required = false) String city,
                                                @RequestParam(value = "status", required = false) String status,
                                                @RequestParam(value = "price", required = false) Double price,
                                                @RequestParam(value = "numberOfRooms", required = false)
                                                Integer numberOfRooms) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority(Roles.OWNER.name()))) {
            throw new AccessDeniedException("You are not authorized to access this resource.");
        }

        return propertyService.findByAllCriteria(propertyType, city, status, price, numberOfRooms);
    }

    @PutMapping("/owner/{id}/cancelContingency")
    public void cancelContingency(@PathVariable Long id) {
        propertyService.cancelContingency(id);
    }

    @GetMapping("/active-offer")
    public ResponseEntity<List<OfferResponse>> getAllActiveOffers() {
        List<OfferResponse> activeOffers = offerServiceImpl.findAllActiveOffers();
        if (activeOffers != null) {
            return new ResponseEntity<>(activeOffers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/owner/{ownerId}/active-offers")
    public List<OfferResponse> getActiveOffersByOwnerId(@PathVariable Long ownerId) {
        System.out.println(ownerId);
        List<OfferResponse> activeOffers = offerServiceImpl.findActiveOffersByOwnerId(ownerId);
        return  activeOffers;
    }

    @GetMapping("/owner/{ownerId}/active-offers/properties")
    public ResponseEntity<List<OfferResponse>> getActiveOfferProperties(@PathVariable Long ownerId) {
        List<OfferResponse> activeOfferProperties = offerServiceImpl.findActiveOffersPropertiesForOwner(ownerId);
        if (activeOfferProperties != null) {
            return new ResponseEntity<>(activeOfferProperties, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/owner/{propertyId}")
    public ResponseEntity<List<OfferResponse>> getOffersByPropertyId(@PathVariable Long propertyId) {
        List<OfferResponse> offersByPropertyId = offerServiceImpl.findOffersByPropertyId(propertyId);
        if (offersByPropertyId != null) {
            return new ResponseEntity<>(offersByPropertyId, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/owner/offer/{offerId}/accept")

    public ResponseEntity<OfferResponse> acceptOffer(@PathVariable Long offerId) throws ChangeSetPersister.NotFoundException {

        OfferResponse response= offerServiceImpl.acceptOffer(offerId);
        return new ResponseEntity<>(response,HttpStatus.ACCEPTED);


    }

    @PostMapping("/owner/offer/{offerId}/reject")

    public ResponseEntity<OfferResponse> rejectOffer(@PathVariable Long offerId) {
        OfferResponse response = null;
        try {
            response = offerServiceImpl.rejectOffer(offerId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

}