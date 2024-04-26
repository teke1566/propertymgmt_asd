package edu.miu.PropertyManagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Entity

@ToString
@Data
public class Property  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String propertyName;

    private String propertyType;
    private String description;
    private int numberOfRooms;
    private Double rentAmount;

    private Double securityDepositAmount;
    private Integer numberOfBathRooms;
    private String imageUrl;

    private double price;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate postedDate;
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_address")
    private Address address;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
      private User users_id;
    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "favotite_list_id")
    private FavoriteList favoriteList;

}
