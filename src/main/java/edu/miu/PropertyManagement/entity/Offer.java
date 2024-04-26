package edu.miu.PropertyManagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import edu.miu.PropertyManagement.enums.OfferStatus;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "offer")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;


    @ManyToOne
    @JoinColumn(name = "customer_id")
    User user;
    @Enumerated(EnumType.STRING)
    private OfferStatus status;
    private Double amount;

}
