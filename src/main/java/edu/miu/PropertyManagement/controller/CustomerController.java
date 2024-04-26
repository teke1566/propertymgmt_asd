package edu.miu.PropertyManagement.controller;
import com.itextpdf.text.DocumentException;
import edu.miu.PropertyManagement.entity.*;
import edu.miu.PropertyManagement.entity.dto.response.OfferResponse;
import edu.miu.PropertyManagement.service.PropertyService;
import edu.miu.PropertyManagement.service.impl.CustomerServiceImpl;
import edu.miu.PropertyManagement.service.impl.FavoriteListService;
import edu.miu.PropertyManagement.service.impl.MessageService;
import edu.miu.PropertyManagement.service.impl.OfferServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.ByteArrayInputStream;
import java.util.List;
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    @Autowired
    private CustomerServiceImpl customerServiceImpl;
    @Autowired
    private  MessageService messageService;
    @Autowired
    private  FavoriteListService favoriteListService;
    @Autowired
    private  OfferServiceImpl offerServiceImpl;
    @Autowired
    private   PropertyService propertyService;


    @GetMapping
    public List<User> getAllCustomer(){
        return customerServiceImpl.getAllCustomers();
    }

    @GetMapping("/offer-history/{customerId}")
    public List<OfferResponse> getOfferHistoryByCustomerId(@PathVariable Long customerId) {
        return offerServiceImpl.findByCustomerId(customerId);
    }


    //http://localhost:8080/api/v1/customer/1/active-offers

    @GetMapping("/{customerId}/active-offers")//offer made by specific customer
    public List<OfferResponse> getActiveOffersByCustomerId(@PathVariable Long customerId){
        return  offerServiceImpl.findActiveOffersByCustomerId(customerId);
        //customer...properties
        //owner offer....properties...customer
    }



    //http://localhost:8080/api/v1/customer/offers/5/cancel
    @PostMapping("/offers/{offerId}/cancel")
    public ResponseEntity<String> cancelOffer(@PathVariable Long offerId){
        if(offerServiceImpl.canCancelOffer(offerId)){
            offerServiceImpl.cancelOffer(offerId);
            return ResponseEntity.ok("Offer canceled successfully");
        }
        else{
            return new ResponseEntity<>("Cannot cancel offer after contingency.", HttpStatus.BAD_REQUEST);
        }
    }
    //http://localhost:8080/api/v1/customer/offers/2/receipt
    @GetMapping("/offers/{offerId}/receipt")

    public ResponseEntity<byte[]> generateReceipt(@PathVariable Long offerId){
        try {
            ByteArrayInputStream bis= offerServiceImpl.generateReceipt(offerId);
            HttpHeaders headers= new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment","receipt.pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(bis.readAllBytes(),headers,HttpStatus.OK);


        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }



    //http://localhost:8080/api/v1/customer/place-offer?propertyId=1&userId=5&amount=100000
    @PostMapping("/place-offer")
    public ResponseEntity<OfferResponse> placeOffer(
            @RequestParam String propertyId,
            @RequestParam Long userId,
            @RequestParam Double amount) {
        OfferResponse offer = customerServiceImpl.placeOffer(Long.valueOf(propertyId), userId, amount);
        if (offer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(offer, HttpStatus.CREATED);
    }


    @PostMapping("/send-messages")
    public ResponseEntity<Message> sendMessage(@RequestBody Message message) {
        Message savedMessage = messageService.saveMessage(message);
        return new ResponseEntity<>(savedMessage, HttpStatus.CREATED);
    }

    @GetMapping("/sent-messages/{userId}")

    public List<Message> sentMessages(@PathVariable Long userId){
        return messageService.getSentMessages(userId);
    }

    @GetMapping("/received-messages/{recipientId}")
    public  List<Message> receivedMessages(@PathVariable Long recipientId){
        return messageService.getReceivedMessages(recipientId);
    }


    //http://localhost:8080/api/v1/customer/add-favorites/2?propertyId=1&name="My Fav"

    @PostMapping("/add-favorites/{customerId}")
    public ResponseEntity<Object> addSavedProperty(@PathVariable Long customerId,
                                                   @RequestParam Long propertyId,
                                                   @RequestParam String name){
        favoriteListService.addToFavoriteList(customerId,propertyId,name);
      return new ResponseEntity<>(HttpStatus.CREATED);
    }



    @GetMapping("get-favorites/{customerId}")
    public List<FavoriteList> getSavedProperties(@PathVariable Long customerId){
        return favoriteListService.getFavoriteList(customerId);
    }

    @DeleteMapping("/saved-properties/{savedPropertyId}")
    public ResponseEntity<Object> removeSavedProperty(@PathVariable Long savedPropertyId){
        favoriteListService.removeFavoriteList(savedPropertyId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/offer/{offerId}/accept")

    public ResponseEntity<OfferResponse> acceptOffer(@PathVariable Long offerId) throws ChangeSetPersister.NotFoundException {

        OfferResponse response= offerServiceImpl.acceptOffer(offerId);
        return new ResponseEntity<>(response,HttpStatus.ACCEPTED);


    }



}
