package edu.miu.PropertyManagement.service.impl;

import edu.miu.PropertyManagement.entity.Property;
import edu.miu.PropertyManagement.entity.User;
import edu.miu.PropertyManagement.entity.dto.request.PagingRequest;
import edu.miu.PropertyManagement.entity.dto.response.UserResponseDto;
import edu.miu.PropertyManagement.enums.Roles;
import edu.miu.PropertyManagement.repository.PropertyRepository;
import edu.miu.PropertyManagement.repository.UserRepository;
import edu.miu.PropertyManagement.service.AdminService;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
@Transactional
public class AdminserviceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<UserResponseDto> findAll() {

        List<UserResponseDto> users = new ArrayList<>();
        userRepository.findAllExceptAdmin().forEach(u -> {
            UserResponseDto user = modelMapper.map(u, UserResponseDto.class);
            user.setRole(u.getRole().get(0).getRole());
            users.add(user);
        });
        return users;
    }

    @Override
    public List<UserResponseDto> getAllCustomer() {
        List<UserResponseDto> users = findAll()
                .stream()
                .filter(lst -> lst.getRole().equals(Roles.CUSTOMER))
                .toList();
        return  users;
    }

    @Override
    public Page<User> pagination(PagingRequest pagingRequest) {
        var direction = (pagingRequest.isAscending()) ? Sort.Direction.ASC : Sort.Direction.DESC;
        var request = PageRequest
                .of(pagingRequest.getPage(), pagingRequest.getPageSize(), direction,pagingRequest.getSortBy());
        return userRepository.findAll(request);
    }

    @Override
    public User getCustomerById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<UserResponseDto> getAllOwners() {
                List<UserResponseDto> users = findAll()
                .stream()
                .filter(lst -> lst.getRole().equals(Roles.OWNER))
                .toList();
        return  users;
    }

    @Override
    public User getOwnerById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }
    @Override
    public void activateUser(long id) {
        userRepository.activateUser(id,"active");
    }

    @Override
    public void blockUser(long id) {
        userRepository.activateUser(id,"inactive");
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<Property> getAllProperty() {
        return propertyRepository.findAll();
    }

    @Override
    public Property getPropertyById(long id) {
        return propertyRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteProperty(long id) {
        propertyRepository.deleteById(id, "PENDING");
    }

    @Override
    public void resetPassword(long id) {
        userRepository.resetPassword(id, "1234");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}