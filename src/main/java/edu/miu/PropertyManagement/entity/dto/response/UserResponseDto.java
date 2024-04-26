package edu.miu.PropertyManagement.entity.dto.response;

import edu.miu.PropertyManagement.entity.dto.PropertyDto;

import edu.miu.PropertyManagement.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private long id;
    private String name;

    private String email;
    private Roles role;

    private List<PropertyDto> properties;


}