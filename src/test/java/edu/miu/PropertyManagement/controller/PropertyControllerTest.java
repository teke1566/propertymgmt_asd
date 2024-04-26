package edu.miu.PropertyManagement.controller;

import edu.miu.PropertyManagement.entity.Property;
import edu.miu.PropertyManagement.entity.dto.response.PropertyResponse;
import edu.miu.PropertyManagement.service.PropertyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PropertyControllerTest {

    @Mock
    private PropertyService propertyService;

    @InjectMocks
    private PropertyController propertyController;

    @Test
    public void testGetAllProperties() {
        List<PropertyResponse> expectedResponse = Collections.emptyList();
        when(propertyService.getAllProperty()).thenReturn(expectedResponse);
            List<PropertyResponse> actualResponse = propertyController.getAllProperties();

        assertEquals(expectedResponse, actualResponse);
        verify(propertyService).getAllProperty();
    }

    @Test
    public void testUpdateProperty() {
        // Arrange
        Property property = new Property();
        Long id = 1L;
        when(propertyService.updateProperty(any(Property.class), eq(id))).thenReturn(property);

        // Act
        Property updatedProperty = propertyController.updateProperty(property, id);

        // Assert
        assertEquals(property, updatedProperty);
        verify(propertyService).updateProperty(property, id);
    }

    @Test
    public void testDeleteProperty() {
        // Arrange
        Long id = 1L;

        // Act
        propertyController.deleteProperty(id);

        // Assert
        verify(propertyService).deleteProperty(id);
    }

    @Test
    public void testUpdateToPending() {
        // Arrange
        Long id = 1L;
        PropertyResponse expectedResponse = new PropertyResponse(); // define expected response
        when(propertyService.updateToPending(id)).thenReturn(expectedResponse);

        // Act
        PropertyResponse actualResponse = propertyController.UpdateToPending(id);

        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(propertyService).updateToPending(id);
    }

    @Test
    public void testUpdateToAvailable() {
        // Arrange
        Long id = 1L;
        PropertyResponse expectedResponse = new PropertyResponse(); // define expected response
        when(propertyService.updateToAvailable(id)).thenReturn(expectedResponse);

        // Act
        PropertyResponse actualResponse = propertyController.UpdateToAvailable(id);

        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(propertyService).updateToAvailable(id);
    }

    @Test
    public void testUpdateToCONTINGENT() {
        // Arrange
        Long id = 1L;
        PropertyResponse expectedResponse = new PropertyResponse(); // define expected response
        when(propertyService.updateToCONTINGENT(id)).thenReturn(expectedResponse);

        // Act
        PropertyResponse actualResponse = propertyController.UpdateToCONTINGENT(id);

        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(propertyService).updateToCONTINGENT(id);
    }

    @Test
    public void testCancelContingency() {
        // Arrange
        Long id = 1L;

        // Act
        propertyController.cancelContingency(id);

        // Assert
        verify(propertyService).cancelContingency(id);
    }

    @Test
    public void testPostPropertyAccessDenied() {
        // Arrange
        Property property = new Property();
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act & Assert
        AccessDeniedException exception = assertThrows(AccessDeniedException.class, () -> propertyController.postProperty(property));
        assertEquals("You are not authorized to access this resource.", exception.getMessage());
        verify(propertyService, never()).post(any(Property.class));
    }
}

