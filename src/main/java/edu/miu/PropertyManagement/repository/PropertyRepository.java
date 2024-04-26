package edu.miu.PropertyManagement.repository;


import edu.miu.PropertyManagement.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property,Long> {

    @Query("select p from Property  p where p.address.city=?1")
    public List<Property>propertiesbyaddress(String city);

    List<Property> findAllById(Long ownerId);
    @Modifying
    @Query("DELETE FROM Property u WHERE u.id = :id AND u.status <> :value")
    void deleteById(long id, String value);
}