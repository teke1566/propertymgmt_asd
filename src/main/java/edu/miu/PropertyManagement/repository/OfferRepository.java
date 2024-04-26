package edu.miu.PropertyManagement.repository;

import edu.miu.PropertyManagement.entity.Offer;
import edu.miu.PropertyManagement.entity.Status;
import edu.miu.PropertyManagement.entity.dto.response.OfferResponse;
import edu.miu.PropertyManagement.entity.dto.response.OfferResponseWithProperty;
import edu.miu.PropertyManagement.enums.OfferStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository  extends JpaRepository<Offer,Long> {

    List<Offer> findByUserId(Long userId);

    @Query("SELECT o FROM Offer o WHERE o.user.id = :customerId AND o.status = 'PENDING'")
    List<Offer> findActiveOffersByCustomerId(Long customerId);

    @Query("SELECT NEW edu.miu.PropertyManagement.entity.dto.response.OfferResponse(o.id, o.user.id, o.user.name, o.user.email, o.status, o.amount) FROM Offer o WHERE o.status = :status")
    List<OfferResponse> findByStatus(@Param("status") OfferStatus status);



    List<Offer> findByProperty_Id(Long propertyId);

}
