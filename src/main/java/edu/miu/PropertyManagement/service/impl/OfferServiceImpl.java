package edu.miu.PropertyManagement.service.impl;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import edu.miu.PropertyManagement.entity.Offer;
import edu.miu.PropertyManagement.entity.Property;
import edu.miu.PropertyManagement.entity.Status;
import edu.miu.PropertyManagement.entity.User;
import edu.miu.PropertyManagement.entity.dto.response.OfferResponse;
import edu.miu.PropertyManagement.enums.OfferStatus;
import edu.miu.PropertyManagement.enums.Roles;
import edu.miu.PropertyManagement.repository.OfferRepository;
import edu.miu.PropertyManagement.repository.PropertyRepository;
import edu.miu.PropertyManagement.repository.UserRepository;
import edu.miu.PropertyManagement.service.OfferService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class OfferServiceImpl  implements OfferService {
    @Autowired
    private  OfferRepository offerRepository;
    @Autowired
    private  PropertyRepository propertyRepository;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

   @Override
    public List<OfferResponse> findByCustomerId(Long customerId) {

        var offer= offerRepository.findByUserId(customerId);


       var add = offer.stream()
               .map(offers -> {

                   return modelMapper.map(offers,OfferResponse.class);
               })
               .toList();
  return  add;

    }
    @Override
    public List<OfferResponse> findActiveOffersByCustomerId(Long customerId){
        User customer= userRepository.findById(customerId).get();
      if (customer.getRole().equals(Roles.CUSTOMER)) {
          var offer= offerRepository.findActiveOffersByCustomerId(customerId);
          offer.stream().map(offer1 -> modelMapper.map(offer1,OfferResponse.class)).toList();
      }
        System.out.println("you don't have CUSTOMER acess");
      return null;

    }

    @Override
    public List<OfferResponse> findActiveOffersByOwnerId(Long ownerId) {
        User user = userRepository.findById(ownerId).orElse(null);
        if (user != null) {
            boolean isOwner = user.getRole().stream()
                    .anyMatch(role -> role.getRole() == Roles.OWNER);
            if (isOwner) {
                    var obj = offerRepository.findActiveOffersByCustomerId(ownerId);

                    var add = obj.stream()
                            .map(offer -> {

                                return modelMapper.map(offer,OfferResponse.class);
                            })
                            .toList();
                return add;
            } else {
                System.out.println("You don't have OWNER access");
            }
        } else {
            System.out.println("User not found with id: " + ownerId);
        }
        return Collections.emptyList();
    }



    @Override
    public List<OfferResponse> findAllActiveOffers() {
        return offerRepository.findByStatus(OfferStatus.PENDING);
    }




    @Override
    public List<OfferResponse> findActiveOffersPropertiesForOwner(Long ownerId){
        // return properties associated with specific offer
        var offers= findActiveOffersByOwnerId(ownerId);

        offers.stream().map(offerResponse -> modelMapper.map(offerResponse,OfferResponse.class)).toList();

        return offers;

    }

    @Override
    public  List<OfferResponse> findOffersByPropertyId(Long propertyId){

        return offerRepository.findByProperty_Id(propertyId).stream().map(OfferResponse::fromOffer).toList();
    }



    @Override
    public OfferResponse rejectOffer(Long offerId) throws ChangeSetPersister.NotFoundException {
        Offer offer = offerRepository.findById(offerId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        offer.setStatus(OfferStatus.REJECTED);
        offer.getProperty().setStatus(Status.AVAILABLE);
        offerRepository.save(offer);
        return OfferResponse.fromOffer(offer);
    }
    @Override
    public OfferResponse acceptOffer(Long offerId) throws ChangeSetPersister.NotFoundException {
        Offer offer =offerRepository.findById(offerId).orElseThrow((ChangeSetPersister.NotFoundException::new));
        offer.setStatus(OfferStatus.ACCEPTED);
        offer.getProperty().setStatus(Status.CONTINGENT);
        offerRepository.save(offer);

        return OfferResponse.fromOffer(offer);
    }

    /*c.	Cannot cancel offer after 'contingency':
      When implementing the cancel offer functionality,
      add a condition to check if the offer is still
      within the contingency period. If it's not,
       display an error message or disable the cancel option.
     */
    @Override
    public boolean canCancelOffer(Long offerId){
        Optional<Offer> offerOptional= offerRepository.findById(offerId);
        if(offerOptional.isPresent()){
            Offer offer=offerOptional.get();
            Property property= propertyRepository.findById(offer.getProperty().getId()).orElse(null);
            if(property!=null){
                return !offer.getStatus().equals(Status.CONTINGENT);
            }
        }
        return  false;
    }
    @Override
    public void cancelOffer(Long offerId){
        Optional<Offer> offerOptional =offerRepository.findById(offerId);

        if (offerOptional.isPresent()){
            Offer offer= offerOptional.get();
            Property property=propertyRepository.findById(offer.getProperty().getId()).orElse(null);

            if(property!=null){
                property.setStatus(Status.AVAILABLE);
                propertyRepository.save(property);
            }
        }
    }

    /*
    * d.	Download/Print receipt as PDF or Excel:
        Create an endpoint to generate receipts for the
        * customer's offers. You can use libraries like
        * Apache POI for Excel files or iText for PDF
        * files to create and export the receipt documents.
    * */

    @Override
    public ByteArrayInputStream generateReceipt(Long offerId) throws DocumentException {
        Optional<Offer> offerOptional =offerRepository.findById(offerId);
        if(offerOptional.isPresent()){
            Offer offer=offerOptional.get();
            ByteArrayOutputStream out =new ByteArrayOutputStream();

            Document document= new Document();
            PdfWriter.getInstance(document,out);
            document.open();
            Font font=new Font(Font.FontFamily.TIMES_ROMAN,12,Font.BOLD);
            Paragraph paragraph=new Paragraph("Offer ID: "+offer.getId(),font);
            Paragraph paragraph1=new Paragraph("Offered By: "+offer.getUser().getName());
            Paragraph paragraph2=new Paragraph("Property Name: "+offer.getProperty().getPropertyName(),font);
            Paragraph paragraph3=new Paragraph("Property Price: "+offer.getProperty().getPrice(),font);
            Paragraph paragraph4=new Paragraph("Offered Amount: "+offer.getAmount(),font);
            document.add(paragraph1);document.add(paragraph2);document.add(paragraph3);
            document.add(paragraph4);
            document.close();
            return new ByteArrayInputStream(out.toByteArray());
        }
        else{
            return null;
        }

    }
}
