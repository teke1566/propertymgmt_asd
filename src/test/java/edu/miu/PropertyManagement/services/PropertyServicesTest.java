package edu.miu.PropertyManagement.services;

import edu.miu.PropertyManagement.entity.Property;
import edu.miu.PropertyManagement.entity.User;
import edu.miu.PropertyManagement.enums.Roles;
import edu.miu.PropertyManagement.repository.PropertyRepository;
import edu.miu.PropertyManagement.repository.UserRepository;
import edu.miu.PropertyManagement.service.impl.Propertyserviceimpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PropertyServicesTest {

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private Propertyserviceimpl propertyService;



    @Test
    public void testPropertiesByUserId_NotOwner() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User(Roles.OWNER)));

        // Act
        List<Property> actualProperties = propertyService.propertiesByUserId(userId);

        // Assert
        assertEquals(null, actualProperties);
        verify(userRepository).findById(userId);
        verify(propertyRepository, never()).findAllById(userId);
    }


}



