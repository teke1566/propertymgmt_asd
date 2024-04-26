package edu.miu.PropertyManagement.repository;

import edu.miu.PropertyManagement.entity.Role;
import edu.miu.PropertyManagement.enums.Roles;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends CrudRepository<Role,Long> {
    Role findByRole(Roles role);
}
