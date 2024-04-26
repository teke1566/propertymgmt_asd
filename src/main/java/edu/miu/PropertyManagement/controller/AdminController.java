package edu.miu.PropertyManagement.controller;

import edu.miu.PropertyManagement.entity.Property;
import edu.miu.PropertyManagement.entity.User;
import edu.miu.PropertyManagement.entity.dto.request.PagingRequest;
import edu.miu.PropertyManagement.entity.dto.response.UserResponseDto;
import edu.miu.PropertyManagement.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/customer")
    public List<UserResponseDto> getAllCustomer(){
        return adminService.getAllCustomer();
    }

    @PostMapping("/pagination")
    public Page<User> pagination(@RequestBody PagingRequest pagingRequest) {
        return adminService.pagination(pagingRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/customer/{id}")
    public User getCustomerById(@PathVariable("id") long id){
        return adminService.getCustomerById(id);
    }

    //Owner
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/owner")
    public List<UserResponseDto> getAllOwners(){
        return adminService.getAllOwners();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/owner/{id}")
    public User getOwnerById(@PathVariable("id") long id){
        return adminService.getOwnerById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void saveUser(@RequestBody User user) {
        adminService.saveUser(user);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user/activate/{id}")
    public void activateUser(@PathVariable("id") long id) {
        adminService.activateUser(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user/block/{id}")
    public void blockUser(@PathVariable("id") long id) {
        adminService.blockUser(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable("id")  long id) {
        adminService.deleteUser(id);
    }

    //Property
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/property")
    public List<Property> getAllProperty(){
        return adminService.getAllProperty();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/property/{id}")
    public Property getPropertyById(@PathVariable("id") long id){
        return adminService.getPropertyById(id);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/property/{id}")
    public void deleteProperty(@PathVariable("id")  long id) {
        adminService.deleteProperty(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/reset-password/{id}")
    public void resetPassword(@PathVariable("id") long id) {
        adminService.resetPassword(id);
    }
}